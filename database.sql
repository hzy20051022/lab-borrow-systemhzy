CREATE DATABASE IF NOT EXISTS lab_borrow DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE lab_borrow;

DROP TABLE IF EXISTS borrow;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    realname VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role INT DEFAULT 0 COMMENT '0-学生, 1-管理员',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE equipment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    model VARCHAR(100),
    location VARCHAR(100),
    description TEXT,
    status INT DEFAULT 0 COMMENT '0-空闲, 1-借出中, 2-维修中',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE borrow (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    equipment_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    expected_return_date DATE NOT NULL,
    actual_return_date DATE,
    purpose VARCHAR(500),
    status INT DEFAULT 0 COMMENT '0-待审核, 1-已通过, 2-已拒绝, 3-已撤销, 4-已归还',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO user (username, password, realname, email, phone, role) VALUES
('admin', '123456', '系统管理员', 'admin@lab.edu.cn', '13800138000', 1),
('student', '123456', '张同学', 'zs@lab.edu.cn', '13800138001', 0),
('lisi', '123456', '李同学', 'ls@lab.edu.cn', '13800138002', 0);

INSERT INTO equipment (name, category, model, location, description, status) VALUES
('高精度示波器', '电子仪器', 'Tektronix DPO4054', '3号楼201', '500MHz带宽,4通道数字示波器', 0),
('数字万用表', '电子仪器', 'Fluke 8846A', '3号楼201', '6.5位高精度台式万用表', 0),
('高性能工作站', '计算机', 'Dell Precision 7920', '1号楼101', '双路Xeon CPU,128G内存,深度学习工作站', 0),
('3D打印机', '机械设备', 'Ultimaker S5', '3号楼301', '双喷头FDM打印机,支持多材料', 0),
('激光切割机', '机械设备', 'Epilog Fusion Pro', '3号楼302', '120W CO2激光切割机', 2),
('光学显微镜', '光学仪器', 'Olympus BX53M', '2号楼201', '金相显微镜,支持明暗场观察', 0),
('光谱仪', '光学仪器', 'Ocean Optics HR4000', '2号楼202', '高分辨率光纤光谱分析仪', 1),
('高速摄像机', '电子仪器', 'Phantom VEO 4K', '3号楼203', '4K分辨率,1000fps高速摄影', 0);

INSERT INTO borrow (user_id, equipment_id, borrow_date, expected_return_date, purpose, status) VALUES
(2, 1, '2025-01-15', '2025-01-20', '电子电路实验波形测量', 0),
(3, 3, '2025-01-10', '2025-01-25', '毕业论文机器学习仿真计算', 1);
