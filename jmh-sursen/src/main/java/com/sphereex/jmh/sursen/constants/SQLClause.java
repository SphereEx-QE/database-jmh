package com.sphereex.jmh.sursen.constants;

public interface SQLClause {

    String USER_CREATION = "CREATE TABLE `TABLE_NAME` (\n" +
            "  `id` varchar(64) NOT NULL COMMENT '主键',\n" +
            "  `name` varchar(50) NOT NULL COMMENT '姓名',\n" +
            "  `birthday` varchar(80) DEFAULT NULL COMMENT '生日',\n" +
            "  `gender` char(1) DEFAULT NULL COMMENT '性别 M-男 F-女',\n" +
            "  `nationality` char(2) DEFAULT NULL COMMENT '国籍',\n" +
            "  `contact_person` varchar(50) DEFAULT NULL COMMENT '联系人',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `version` int(11) DEFAULT NULL COMMENT '数据版本',\n" +
            "  `updator` varchar(64) DEFAULT NULL COMMENT '更新人',\n" +
            "  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `tb_f_user_nationality_index` (`nationality`),\n" +
            "  KEY `tb_f_user_create_time_index` (`create_time`) USING BTREE,\n" +
            "  KEY `tb_f_user_update_time_index` (`update_time`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    String USER_CERT_CREATION = "CREATE TABLE `TABLE_NAME` (\n" +
            "  `id` varchar(64) NOT NULL COMMENT '主键',\n" +
            "  `user_id` varchar(64) NOT NULL COMMENT '用户ID',\n" +
            "  `cert_type` varchar(64) NOT NULL COMMENT '证件类型',\n" +
            "  `cert_no` varchar(20) NOT NULL COMMENT '证件号码',\n" +
            "  `cert_key` varchar(128) DEFAULT NULL COMMENT '身份标识',\n" +
            "  `issuing_unit` varchar(64) DEFAULT NULL COMMENT '签发单位',\n" +
            "  `effective_date` date DEFAULT NULL COMMENT '证件生效时间',\n" +
            "  `expire_date` date DEFAULT NULL COMMENT '有效期',\n" +
            "  `master` tinyint(1) NOT NULL COMMENT '主证件标识',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `version` int(11) DEFAULT NULL COMMENT '数据版本',\n" +
            "  `updator` varchar(64) DEFAULT NULL COMMENT '更新人',\n" +
            "  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `tb_f_user_cert_user_id_index` (`user_id`),\n" +
            "  KEY `tb_f_user_cert_cert_key_index` (`cert_key`),\n" +
            "  KEY `tb_f_user_create_time_index` (`create_time`) USING BTREE,\n" +
            "  KEY `tb_f_user_update_time_index` (`update_time`) USING BTREE,\n" +
            "  KEY `cert_type` (`cert_type`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    String USER_CONTACT_CREATION = "CREATE TABLE `TABLE_NAME` (\n" +
            "  `id` varchar(64) NOT NULL COMMENT '主键',\n" +
            "  `user_id` varchar(64) NOT NULL COMMENT '用户ID',\n" +
            "  `area_code` varchar(6) NOT NULL COMMENT '手机区号',\n" +
            "  `phone` varchar(80) NOT NULL COMMENT '手机号',\n" +
            "  `email` varchar(80) DEFAULT NULL COMMENT '邮箱',\n" +
            "  `master` tinyint(1) NOT NULL COMMENT '主证件标识',\n" +
            "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  `version` int(11) DEFAULT NULL COMMENT '数据版本',\n" +
            "  `updator` varchar(64) DEFAULT NULL COMMENT '更新人',\n" +
            "  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识',\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `tb_f_user_contact_user_id_index` (`user_id`),\n" +
            "  KEY `tb_f_user_create_time_index` (`create_time`) USING BTREE,\n" +
            "  KEY `tb_f_user_update_time_index` (`update_time`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
}
