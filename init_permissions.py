import pymysql
import sys

# 设置编码
sys.stdout.reconfigure(encoding='utf-8')

# 连接数据库
conn = pymysql.connect(
    host='localhost',
    user='root',
    password='12345678',
    database='course',
    charset='utf8mb4'
)

cursor = conn.cursor()

# 1. 角色表
create_role_table = '''
CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色代码',
    role_name VARCHAR(100) NOT NULL COMMENT '角色名称',
    description TEXT COMMENT '角色描述',
    status VARCHAR(20) DEFAULT 'active' COMMENT '状态: active/inactive',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
'''

# 2. 权限表
create_permission_table = '''
CREATE TABLE IF NOT EXISTS permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    permission_code VARCHAR(100) NOT NULL UNIQUE COMMENT '权限代码',
    permission_name VARCHAR(100) NOT NULL COMMENT '权限名称',
    module VARCHAR(50) COMMENT '模块: student/teacher/course/selection/user/role',
    action VARCHAR(50) COMMENT '操作: view/add/edit/delete',
    description TEXT COMMENT '权限描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
'''

# 3. 角色权限关联表
create_role_permission_table = '''
CREATE TABLE IF NOT EXISTS role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE,
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
'''

# 4. 用户角色关联表
create_user_role_table = '''
CREATE TABLE IF NOT EXISTS user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
'''

# 5. 用户权限关联表（直接权限）
create_user_permission_table = '''
CREATE TABLE IF NOT EXISTS user_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_permission (user_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
'''

# 执行建表
tables = [
    ('role', create_role_table),
    ('permission', create_permission_table),
    ('role_permission', create_role_permission_table),
    ('user_role', create_user_role_table),
    ('user_permission', create_user_permission_table)
]

for table_name, sql in tables:
    try:
        cursor.execute(sql)
        conn.commit()
        print(f"[OK] {table_name} 表创建成功")
    except Exception as e:
        print(f"[FAIL] {table_name} 表创建失败: {e}")

# 插入默认角色
roles = [
    ('admin', '系统管理员', '拥有所有权限'),
    ('teacher', '教师', '教师角色'),
    ('student', '学生', '学生角色'),
    ('manager', '管理员', '教务管理员')
]

for role_code, role_name, desc in roles:
    try:
        cursor.execute('''
            INSERT IGNORE INTO role (role_code, role_name, description)
            VALUES (%s, %s, %s)
        ''', (role_code, role_name, desc))
        conn.commit()
        print(f"[OK] 角色 {role_name} 创建成功")
    except Exception as e:
        print(f"[FAIL] 角色 {role_name} 创建失败: {e}")

# 插入权限
permissions = [
    # 学生管理权限
    ('student:view', '查看学生', 'student', 'view'),
    ('student:add', '添加学生', 'student', 'add'),
    ('student:edit', '编辑学生', 'student', 'edit'),
    ('student:delete', '删除学生', 'student', 'delete'),
    
    # 老师管理权限
    ('teacher:view', '查看老师', 'teacher', 'view'),
    ('teacher:add', '添加老师', 'teacher', 'add'),
    ('teacher:edit', '编辑老师', 'teacher', 'edit'),
    ('teacher:delete', '删除老师', 'teacher', 'delete'),
    
    # 课程管理权限
    ('course:view', '查看课程', 'course', 'view'),
    ('course:add', '添加课程', 'course', 'add'),
    ('course:edit', '编辑课程', 'course', 'edit'),
    ('course:delete', '删除课程', 'course', 'delete'),
    
    # 选课管理权限
    ('selection:view', '查看选课', 'selection', 'view'),
    ('selection:add', '添加选课', 'selection', 'add'),
    ('selection:edit', '编辑选课', 'selection', 'edit'),
    ('selection:delete', '删除选课', 'selection', 'delete'),
    
    # 用户管理权限
    ('user:view', '查看用户', 'user', 'view'),
    ('user:add', '添加用户', 'user', 'add'),
    ('user:edit', '编辑用户', 'user', 'edit'),
    ('user:delete', '删除用户', 'user', 'delete'),
    
    # 角色管理权限
    ('role:view', '查看角色', 'role', 'view'),
    ('role:add', '添加角色', 'role', 'add'),
    ('role:edit', '编辑角色', 'role', 'edit'),
    ('role:delete', '删除角色', 'role', 'delete'),
]

for perm_code, perm_name, module, action in permissions:
    try:
        cursor.execute('''
            INSERT IGNORE INTO permission (permission_code, permission_name, module, action)
            VALUES (%s, %s, %s, %s)
        ''', (perm_code, perm_name, module, action))
        conn.commit()
    except Exception as e:
        pass

print(f"[OK] 权限创建完成")

# 为管理员角色分配所有权限
try:
    cursor.execute("SELECT id FROM role WHERE role_code = 'admin'")
    admin_role = cursor.fetchone()
    if admin_role:
        admin_role_id = admin_role[0]
        cursor.execute("SELECT id FROM permission")
        permissions = cursor.fetchall()
        for perm in permissions:
            perm_id = perm[0]
            cursor.execute('''
                INSERT IGNORE INTO role_permission (role_id, permission_id)
                VALUES (%s, %s)
            ''', (admin_role_id, perm_id))
        conn.commit()
        print(f"[OK] 管理员角色权限配置完成")
except Exception as e:
    print(f"[FAIL] 管理员角色权限配置失败: {e}")

# 为教师角色分配权限
try:
    cursor.execute("SELECT id FROM role WHERE role_code = 'teacher'")
    teacher_role = cursor.fetchone()
    if teacher_role:
        teacher_role_id = teacher_role[0]
        teacher_perms = [
            'student:view', 'course:view', 'selection:view', 'selection:add', 'selection:edit'
        ]
        for perm_code in teacher_perms:
            cursor.execute("SELECT id FROM permission WHERE permission_code = %s", (perm_code,))
            perm = cursor.fetchone()
            if perm:
                cursor.execute('''
                    INSERT IGNORE INTO role_permission (role_id, permission_id)
                    VALUES (%s, %s)
                ''', (teacher_role_id, perm[0]))
        conn.commit()
        print(f"[OK] 教师角色权限配置完成")
except Exception as e:
    print(f"[FAIL] 教师角色权限配置失败: {e}")

# 为学生角色分配权限
try:
    cursor.execute("SELECT id FROM role WHERE role_code = 'student'")
    student_role = cursor.fetchone()
    if student_role:
        student_role_id = student_role[0]
        student_perms = [
            'course:view', 'selection:view', 'selection:add'
        ]
        for perm_code in student_perms:
            cursor.execute("SELECT id FROM permission WHERE permission_code = %s", (perm_code,))
            perm = cursor.fetchone()
            if perm:
                cursor.execute('''
                    INSERT IGNORE INTO role_permission (role_id, permission_id)
                    VALUES (%s, %s)
                ''', (student_role_id, perm[0]))
        conn.commit()
        print(f"[OK] 学生角色权限配置完成")
except Exception as e:
    print(f"[FAIL] 学生角色权限配置失败: {e}")

# 为管理员用户分配管理员角色
try:
    cursor.execute("SELECT id FROM sys_user WHERE username = 'admin'")
    admin_user = cursor.fetchone()
    cursor.execute("SELECT id FROM role WHERE role_code = 'admin'")
    admin_role = cursor.fetchone()
    
    if admin_user and admin_role:
        cursor.execute('''
            INSERT IGNORE INTO user_role (user_id, role_id)
            VALUES (%s, %s)
        ''', (admin_user[0], admin_role[0]))
        conn.commit()
        print(f"[OK] 管理员用户角色分配完成")
except Exception as e:
    print(f"[FAIL] 管理员用户角色分配失败: {e}")

# 为学生用户分配学生角色
try:
    cursor.execute("SELECT id FROM sys_user WHERE user_type = 'student'")
    students = cursor.fetchall()
    cursor.execute("SELECT id FROM role WHERE role_code = 'student'")
    student_role = cursor.fetchone()
    
    if student_role:
        for student in students:
            cursor.execute('''
                INSERT IGNORE INTO user_role (user_id, role_id)
                VALUES (%s, %s)
            ''', (student[0], student_role[0]))
        conn.commit()
        print(f"[OK] 学生用户角色分配完成 ({len(students)} 个)")
except Exception as e:
    print(f"[FAIL] 学生用户角色分配失败: {e}")

# 为老师用户分配教师角色
try:
    cursor.execute("SELECT id FROM sys_user WHERE user_type = 'teacher'")
    teachers = cursor.fetchall()
    cursor.execute("SELECT id FROM role WHERE role_code = 'teacher'")
    teacher_role = cursor.fetchone()
    
    if teacher_role:
        for teacher in teachers:
            cursor.execute('''
                INSERT IGNORE INTO user_role (user_id, role_id)
                VALUES (%s, %s)
            ''', (teacher[0], teacher_role[0]))
        conn.commit()
        print(f"[OK] 教师用户角色分配完成 ({len(teachers)} 个)")
except Exception as e:
    print(f"[FAIL] 教师用户角色分配失败: {e}")

print("\n=== 权限管理系统初始化完成 ===")

cursor.close()
conn.close()
