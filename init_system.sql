-- 教务管理系统数据库表结构
-- 创建日期: 2026-03-23

-- 学生表
CREATE TABLE IF NOT EXISTS student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别',
    birth_date DATE COMMENT '出生日期',
    phone VARCHAR(20) COMMENT '电话',
    email VARCHAR(100) COMMENT '邮箱',
    class_name VARCHAR(50) COMMENT '班级',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 老师表
CREATE TABLE IF NOT EXISTS teacher (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    teacher_no VARCHAR(20) NOT NULL UNIQUE COMMENT '工号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别',
    birth_date DATE COMMENT '出生日期',
    phone VARCHAR(20) COMMENT '电话',
    email VARCHAR(100) COMMENT '邮箱',
    department VARCHAR(50) COMMENT '院系',
    title VARCHAR(20) COMMENT '职称',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 课程表
CREATE TABLE IF NOT EXISTS course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) NOT NULL UNIQUE COMMENT '课程代码',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    credits DECIMAL(3,1) COMMENT '学分',
    hours INT COMMENT '学时',
    semester VARCHAR(20) COMMENT '学期',
    description TEXT COMMENT '课程描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 选课表（学生选课）
CREATE TABLE IF NOT EXISTS course_selection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    teacher_id BIGINT COMMENT '授课老师ID',
    selected_date DATE COMMENT '选课日期',
    status VARCHAR(20) DEFAULT 'selected' COMMENT '状态: selected/confirmed/completed/dropped',
    score DECIMAL(5,2) COMMENT '成绩',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id),
    UNIQUE KEY uk_student_course (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 老师教授课程表
CREATE TABLE IF NOT EXISTS teacher_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    teacher_id BIGINT NOT NULL COMMENT '老师ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    academic_year VARCHAR(20) COMMENT '学年',
    semester VARCHAR(20) COMMENT '学期',
    max_students INT DEFAULT 50 COMMENT '最大学生数',
    current_students INT DEFAULT 0 COMMENT '当前学生数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES teacher(id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    UNIQUE KEY uk_teacher_course (teacher_id, course_id, academic_year, semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
-- 学生数据
INSERT INTO student (student_no, name, gender, birth_date, phone, email, class_name) VALUES
('2021001', '张三', '男', '2003-05-15', '13800138001', 'zhangsan@example.com', '计算机2101'),
('2021002', '李四', '女', '2003-08-20', '13800138002', 'lisi@example.com', '计算机2101'),
('2021003', '王五', '男', '2003-03-10', '13800138003', 'wangwu@example.com', '软件工程2101'),
('2021004', '赵六', '女', '2003-11-25', '13800138004', 'zhaoliu@example.com', '软件工程2101'),
('2022001', '钱七', '男', '2004-02-14', '13800138005', 'qianqi@example.com', '计算机2201');

-- 老师数据
INSERT INTO teacher (teacher_no, name, gender, birth_date, phone, email, department, title) VALUES
('T001', '刘教授', '男', '1975-09-10', '13900139001', 'liuprof@example.com', '计算机学院', '教授'),
('T002', '陈副教授', '女', '1980-12-05', '13900139002', 'chenprof@example.com', '计算机学院', '副教授'),
('T003', '王讲师', '男', '1985-06-20', '13900139003', 'wanglec@example.com', '软件学院', '讲师'),
('T004', '李讲师', '女', '1988-03-15', '13900139004', 'lilec@example.com', '数学学院', '讲师');

-- 课程数据
INSERT INTO course (course_code, course_name, credits, hours, semester, description) VALUES
('CS101', '数据结构', 4.0, 64, '2024-1', '计算机专业核心课程'),
('CS102', '操作系统', 3.5, 56, '2024-1', '计算机专业核心课程'),
('CS201', '计算机网络', 3.0, 48, '2024-2', '计算机专业核心课程'),
('CS301', '软件工程', 3.0, 48, '2025-1', '软件工程专业课程'),
('MATH101', '高等数学A', 5.0, 80, '2024-1', '公共基础课'),
('MATH201', '线性代数', 3.0, 48, '2024-2', '公共基础课');

-- 老师教授课程数据
INSERT INTO teacher_course (teacher_id, course_id, academic_year, semester, max_students, current_students) VALUES
(1, 1, '2024-1', '第一学期', 60, 45),
(1, 3, '2024-2', '第二学期', 50, 38),
(2, 2, '2024-1', '第一学期', 55, 42),
(3, 4, '2025-1', '第一学期', 40, 25),
(4, 5, '2024-1', '第一学期', 80, 65),
(4, 6, '2024-2', '第二学期', 70, 58);

-- 选课数据
INSERT INTO course_selection (student_id, course_id, teacher_id, selected_date, status, score) VALUES
(1, 1, 1, '2024-01-15', 'completed', 92.5),
(1, 2, 2, '2024-01-15', 'completed', 88.0),
(1, 5, 4, '2024-01-15', 'completed', 95.0),
(2, 1, 1, '2024-01-15', 'completed', 85.5),
(2, 2, 2, '2024-01-15', 'completed', 90.0),
(3, 1, 1, '2024-01-16', 'completed', 78.0),
(3, 3, 1, '2024-02-20', 'selected', NULL),
(4, 4, 3, '2025-02-20', 'selected', NULL),
(5, 5, 4, '2024-01-15', 'completed', 91.0),
(5, 6, 4, '2024-02-20', 'selected', NULL);

SELECT '数据初始化完成!' AS result;
