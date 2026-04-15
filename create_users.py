import pymysql
from pypinyin import lazy_pinyin

# 连接数据库
conn = pymysql.connect(
    host='localhost',
    user='root',
    password='12345678',
    database='course',
    charset='utf8mb4'
)

cursor = conn.cursor()

# 重建 sys_user 表
cursor.execute("DROP TABLE IF EXISTS sys_user")
conn.commit()

create_user_table = '''
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL DEFAULT '96e79218965eb72c92a549dd5a330112' COMMENT '密码(MD5: 111111)',
    nickname VARCHAR(50) COMMENT '昵称',
    user_type VARCHAR(20) NOT NULL COMMENT '用户类型: admin/student/teacher',
    student_id BIGINT COMMENT '关联学生ID',
    teacher_id BIGINT COMMENT '关联老师ID',
    role VARCHAR(20) DEFAULT 'user' COMMENT '角色',
    status VARCHAR(20) DEFAULT 'active' COMMENT '状态',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
'''
cursor.execute(create_user_table)
conn.commit()
print("sys_user 表创建成功")

# 插入管理员账号
cursor.execute('''
INSERT INTO sys_user (username, password, nickname, user_type, role) 
VALUES ('admin', '96e79218965eb72c92a549dd5a330112', '系统管理员', 'admin', 'admin')
''')
conn.commit()
print("管理员账号创建成功")

# 获取学生数据
cursor.execute("SELECT id, student_no, name, gender, phone, email, class_name FROM student")
students = cursor.fetchall()

# 为每个学生创建账号
for student in students:
    sid, student_no, name, gender, phone, email, class_name = student
    # 生成用户名：姓名全拼
    pinyin_list = lazy_pinyin(name)
    username = ''.join(pinyin_list).lower()
    
    # 检查用户名是否已存在
    cursor.execute("SELECT id FROM sys_user WHERE username = %s", (username,))
    if cursor.fetchone():
        username = f"{username}{student_no[-4:]}"  # 如果重复，加学号后4位
    
    try:
        cursor.execute('''
            INSERT INTO sys_user (username, password, nickname, user_type, student_id, role)
            VALUES (%s, '96e79218965eb72c92a549dd5a330112', %s, 'student', %s, 'student')
        ''', (username, name, sid))
        conn.commit()
        print(f"学生账号创建: {username} -> {name}")
    except Exception as e:
        print(f"创建学生账号失败: {name} - {e}")

# 获取老师数据
cursor.execute("SELECT id, teacher_no, name, gender, phone, email, department, title FROM teacher")
teachers = cursor.fetchall()

# 为每个老师创建账号
for teacher in teachers:
    tid, teacher_no, name, gender, phone, email, department, title = teacher
    # 生成用户名：姓名全拼
    pinyin_list = lazy_pinyin(name)
    username = ''.join(pinyin_list).lower()
    
    # 检查用户名是否已存在
    cursor.execute("SELECT id FROM sys_user WHERE username = %s", (username,))
    if cursor.fetchone():
        username = f"{username}{teacher_no[-4:]}"  # 如果重复，加工号后4位
    
    try:
        cursor.execute('''
            INSERT INTO sys_user (username, password, nickname, user_type, teacher_id, role)
            VALUES (%s, '96e79218965eb72c92a549dd5a330112', %s, 'teacher', %s, 'teacher')
        ''', (username, name, tid))
        conn.commit()
        print(f"老师账号创建: {username} -> {name}")
    except Exception as e:
        print(f"创建老师账号失败: {name} - {e}")

print("\n=== 账号创建完成 ===")
print("初始密码: 111111")

# 显示所有账号
cursor.execute('''
SELECT u.id, u.username, u.nickname, u.user_type, 
       s.student_no, s.name as student_name, 
       t.teacher_no, t.name as teacher_name
FROM sys_user u 
LEFT JOIN student s ON u.student_id = s.id 
LEFT JOIN teacher t ON u.teacher_id = t.id
''')
users = cursor.fetchall()
print("\n账号列表:")
for u in users:
    uid, username, nickname, user_type, student_no, student_name, teacher_no, teacher_name = u
    if user_type == 'admin':
        print(f"  [{user_type}] {username} - {nickname}")
    elif user_type == 'student':
        print(f"  [{user_type}] {username} - {student_name} ({student_no})")
    elif user_type == 'teacher':
        print(f"  [{user_type}] {username} - {teacher_name} ({teacher_no})")

cursor.close()
conn.close()
