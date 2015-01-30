/*
Navicat MySQL Data Transfer

Source Server         : lijingwen
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : bank

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-23 09:59:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `aid` varchar(50) NOT NULL,
  `cid` varchar(20) default NULL,
  `astate` varchar(20) default NULL,
  `apassword` varchar(50) default NULL,
  `abalance` double(8,2) default NULL,
  `atype` varchar(20) default NULL,
  PRIMARY KEY  (`aid`),
  KEY `fk_cid` (`cid`),
  CONSTRAINT `fk_cid` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('000-20150121052017', '000', 'normal', '202cb962ac59075b964b07152d234b70', '7001.00', 'customer');
INSERT INTO `account` VALUES ('000-20150121080736', '000', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('000-20150122100004', '000', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('2011210001-20150117030638', '2011210001', 'frozen', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('2011210222-20150111063022', '2011210222', 'normal', 'ad5176a3437909b8d19e957cf93e8a33', '98502.00', 'customer');
INSERT INTO `account` VALUES ('2011210222-20150111063041', '2011210222', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('2011210586-20150111061327', '2011210586', 'normal', '1fde8c44ecdab47c6705631e6cb31043', '34991.00', 'customer');
INSERT INTO `account` VALUES ('2011210586-20150111061328', '2011210586', 'frozen', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('2011210586-20150111061329', '2011210586', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('2011210586-20150111061343', '2011210586', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('2011210586-20150111061345', '2011210586', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '201.00', 'customer');
INSERT INTO `account` VALUES ('2011210623-20150111062424', '2011210623', 'frozen', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('2011210686-20150111084335', '2011210686', 'normal', '25f9e794323b453885f5181f1b624d0b', '9800.00', 'customer');
INSERT INTO `account` VALUES ('222-20150122034752', '222', 'normal', '202cb962ac59075b964b07152d234b70', '9701.00', 'customer');
INSERT INTO `account` VALUES ('42000000-20150123093025', '42000000', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('42000000-20150123093027', '42000000', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('42000000-20150123093028', '42000000', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('gaoyue', null, null, 'gaoyue', null, 'admin');
INSERT INTO `account` VALUES ('lijingwen', null, null, 'lijingwen', null, 'admin');
INSERT INTO `account` VALUES ('ljw-111', 'ljw', 'normal', '202cb962ac59075b964b07152d234b70', '2101.00', 'customer');
INSERT INTO `account` VALUES ('ljw-20150121024455', 'ljw', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('ljw-20150121025608', 'ljw', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('ljw-20150121025614', 'ljw', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('ljw-222', 'ljw', 'normal', '202cb962ac59075b964b07152d234b70', '1500.00', 'customer');
INSERT INTO `account` VALUES ('p-20150121050354', 'p', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('penghu', null, null, 'penghu', null, 'admin');
INSERT INTO `account` VALUES ('pp-20150122043351', 'pp', 'normal', '202cb962ac59075b964b07152d234b70', '7001.00', 'customer');
INSERT INTO `account` VALUES ('tvxq-20150121031053', 'tvxq', 'normal', '84e3128bc1c7d63bdf3364802a4234db', '6901.00', 'customer');
INSERT INTO `account` VALUES ('tvxq-20150121031129', 'tvxq', 'normal', 'e10adc3949ba59abbe56e057f20f883e', '1.00', 'customer');
INSERT INTO `account` VALUES ('yangfangge', null, null, 'yangfangge', null, 'admin');
INSERT INTO `account` VALUES ('zhang', null, null, null, null, null);
INSERT INTO `account` VALUES ('zhangying', null, null, 'zhangying', null, 'admin');
INSERT INTO `account` VALUES ('zhouyunfeng', null, null, 'zhouyunfeng', null, 'admin');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `cid` varchar(20) NOT NULL,
  `cname` varchar(100) default NULL,
  `csex` varchar(4) default NULL,
  `ctel` varchar(22) default NULL,
  `caddr` varchar(200) default NULL,
  `ccareer` varchar(50) default NULL,
  `cstate` varchar(20) default NULL,
  `cdate` varchar(50) default NULL,
  PRIMARY KEY  (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('000', 'Tom', 'Male', '123', 'America', 'driver', 'ok', '20150121051948');
INSERT INTO `customer` VALUES ('0000', '', 'Male', '', '', '', 'ok', '20150122095503');
INSERT INTO `customer` VALUES ('1000000', 'angela', 'Male', '13716400346', 'china', 'teacher', 'ok', '20150120055536');
INSERT INTO `customer` VALUES ('111111', '222222', 'Male', '333333', '44444', '55555', 'ok', '20150122094921');
INSERT INTO `customer` VALUES ('1233211234567', 'mike', 'Male', '1233211234556', 'hubei', 'teacher', 'ok', '20150122094443');
INSERT INTO `customer` VALUES ('20112100005', 'fangge', 'Male', '130000', 'hubei', 'stu', 'ok', '20150118040001');
INSERT INTO `customer` VALUES ('2011210001', '张三', 'm', '13700000000', ' 华中师范大学', 'CEO', 'off', '20150111061234');
INSERT INTO `customer` VALUES ('2011210002', 'Wangwu', 'Male', '130000', 'hubei', 'stu', 'ok', '20150118030812');
INSERT INTO `customer` VALUES ('2011210003', 'Hannah', 'Male', '130000341', 'hongkong', 'stu', 'ok', '20150118030915');
INSERT INTO `customer` VALUES ('2011210004', 'Kong', 'Male', '18600000', 'japan', 'ceo', 'ok', '20150118031321');
INSERT INTO `customer` VALUES ('2011210212', '李四', 'm', '13700000004', ' 华中师范大学', '学生', 'ok', '20150111061252');
INSERT INTO `customer` VALUES ('2011210222', '周云峰', 'm', '18627854896', '湖北', '师哥', 'ok', '20150111062817');
INSERT INTO `customer` VALUES ('2011210541', '金花花', 'f', '222222222222', ' 华中师范大学', '学生', 'ok', '20150111060203');
INSERT INTO `customer` VALUES ('2011210555', 'Orz', 'Male', '⅜', 'Italy', 'architect', 'ok', '20150118081031');
INSERT INTO `customer` VALUES ('2011210586', '高悦', 'f', '13700000004', ' 华中师范大学', '学生', 'ok', '20150111061111');
INSERT INTO `customer` VALUES ('2011210589', '杨芳阁', 'm', 'HH', 'HH', 'H', 'ok', '20150111085251');
INSERT INTO `customer` VALUES ('2011210623', '张莹', 'f', '13700000000', ' 华中师范大学', '学生', 'ok', '20150111061202');
INSERT INTO `customer` VALUES ('2011210624', '杨芳阁', 'f', '13700000004', ' 华中师范大学', 'student', 'ok', '20150111061145');
INSERT INTO `customer` VALUES ('2011210686', 'YELIN', 'f', '555', '11', '11', 'ok', '20150111084321');
INSERT INTO `customer` VALUES ('2012213333', '王五', 'm', '13700000000', ' 华中师范大学', '学生', 'ok', '20150111061310');
INSERT INTO `customer` VALUES ('222', 'gaoyue', 'Male', '10010', 'guangzhou', 'teacher', 'ok', '20150122034720');
INSERT INTO `customer` VALUES ('400800900', '孙悟饭', 'on', '13716400346', '包子山', '学者', 'ok', '20150107061215');
INSERT INTO `customer` VALUES ('42000000', '李靖雯', 'f', '13xxxxxxxxx', '广东广州', 'student', 'ok', '20150107125620');
INSERT INTO `customer` VALUES ('42000000100', 'Ken', 'm', '15100000000', '飞天历险', '木匠', 'off', '20150108091628');
INSERT INTO `customer` VALUES ('42000000101', '香奈儿', 'f', '13700000000', '巴黎', '设计师', 'ok', '20150109070715');
INSERT INTO `customer` VALUES ('42000000102', '柯南', 'm', '13700000001', '毛利侦探事务所', '侦探', 'ok', '20150109070759');
INSERT INTO `customer` VALUES ('42000000103', '毛利兰', 'f', '13700000001', '毛利侦探事务所', '学生', 'ok', '20150109070825');
INSERT INTO `customer` VALUES ('4200000023', '琪琪', 'on', '13700000001', '包子山', '家庭主妇', 'ok', '20150107061405');
INSERT INTO `customer` VALUES ('4200000024', '神龙', 'm', '1233211234567', '宇宙', '神龙', 'off', '20150107061530');
INSERT INTO `customer` VALUES ('4200000025', '孙悟天', 'm', '1233211234567', '包子山', '抓鱼', 'ok', '20150108083828');
INSERT INTO `customer` VALUES ('4200000026', '小芳', 'f', '1860000000', '包子山', '学生', 'ok', '20150108084941');
INSERT INTO `customer` VALUES ('4200000027', '杜拉格斯', 'm', '15000000000', '胶囊公司', 'CEO', 'ok', '20150109031428');
INSERT INTO `customer` VALUES ('4200000028', '布玛', 'f', '13700000141', '胶囊公司', 'CEO', 'ok', '20150109063716');
INSERT INTO `customer` VALUES ('4200000029', '贝吉达', 'm', '18xxxxxxxxx', '胶囊公司', 'CEO', 'ok', '20150109070102');
INSERT INTO `customer` VALUES ('42000001', '孙悟空', 'm', '18xxxxxxxxx', '包子山', 'strongman', 'ok', '20150107014003');
INSERT INTO `customer` VALUES ('424242424242', 'ljw', 'on', '13716400346', 'guangdouguangzhou', 'student', 'ok', '20150107050125');
INSERT INTO `customer` VALUES ('43421134124', '', 'Male', '', '', '', 'ok', '20150122095031');
INSERT INTO `customer` VALUES ('haha', 'Mandy', 'Male', '11111111', '111111111', 'housewife', 'ok', '20150121034650');
INSERT INTO `customer` VALUES ('lijingwen', '1', 'Male', '1', '1', '1', 'ok', '20150120031741');
INSERT INTO `customer` VALUES ('ljw', '超级无敌金花花', 'f', '10000', 'hahaha', '超人', 'ok', '20150119185300');
INSERT INTO `customer` VALUES ('p', 'p', 'Male', '133432414', 'hubei', 'p', 'ok', '20150121050322');
INSERT INTO `customer` VALUES ('pp', 'penghu', 'Male', '10086', 'hongshan', 'teacher', 'ok', '20150122043311');
INSERT INTO `customer` VALUES ('tvxq', 'Lijingwen', 'Male', '1360000000', 'China', 'student', 'ok', '20150121031007');
INSERT INTO `customer` VALUES ('xxxxxx', 'xxx', 'Male', 'xx', 'xxx', '', 'ok', '20150122095834');

-- ----------------------------
-- Table structure for detail
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail` (
  `did` int(50) NOT NULL auto_increment,
  `aid` varchar(50) default NULL,
  `dcount` varchar(50) default NULL,
  `dmoney` double(8,2) default NULL,
  `ddate` varchar(50) default NULL,
  `dtype` varchar(10) default NULL,
  `d_pre_balance` double(8,2) default NULL,
  `dbalance` double(8,2) default NULL,
  PRIMARY KEY  (`did`),
  KEY `fk_dcount` (`dcount`),
  KEY `fk_aid` (`aid`),
  CONSTRAINT `fk_aid` FOREIGN KEY (`aid`) REFERENCES `account` (`aid`),
  CONSTRAINT `fk_dcount` FOREIGN KEY (`dcount`) REFERENCES `account` (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of detail
-- ----------------------------
INSERT INTO `detail` VALUES ('101', '2011210686-20150111084335', '2011210686-20150111084335', '500.00', '20150111084551', 'deposit', '1.00', '501.00');
INSERT INTO `detail` VALUES ('102', '2011210686-20150111084335', '2011210686-20150111084335', '500.00', '20150111084614', 'draw', '501.00', '1.00');
INSERT INTO `detail` VALUES ('103', '2011210686-20150111084335', '2011210686-20150111084335', '1.00', '20150111084647', 'draw', '1.00', '0.00');
INSERT INTO `detail` VALUES ('104', '2011210686-20150111084335', '2011210686-20150111084335', '10000.00', '20150111084904', 'deposit', '0.00', '10000.00');
INSERT INTO `detail` VALUES ('105', '2011210686-20150111084335', '2011210586-20150111061345', '200.00', '20150111084951', 'transOut', '10000.00', '9800.00');
INSERT INTO `detail` VALUES ('106', '2011210586-20150111061345', '2011210686-20150111084335', '200.00', '20150111084951', 'transIn', '1.00', '201.00');
INSERT INTO `detail` VALUES ('107', '2011210586-20150111061327', '2011210586-20150111061327', '10000.00', '20150117030823', 'deposit', '27001.00', '37001.00');
INSERT INTO `detail` VALUES ('108', '2011210586-20150111061327', '2011210586-20150111061327', '2000.00', '20150117030833', 'draw', '37001.00', '35001.00');
INSERT INTO `detail` VALUES ('109', '2011210586-20150111061327', '2011210586-20150111061327', '90.00', '20150117030846', 'deposit', '35001.00', '35091.00');
INSERT INTO `detail` VALUES ('110', 'ljw-222', 'ljw-222', '100.00', '20150117030846', 'deposit', '1.00', '101.00');
INSERT INTO `detail` VALUES ('111', 'ljw-222', 'ljw-222', '100.00', '20150119095158', 'deposit', '101.00', '201.00');
INSERT INTO `detail` VALUES ('112', 'ljw-222', 'ljw-222', '100.00', '20150119095224', 'deposit', '201.00', '301.00');
INSERT INTO `detail` VALUES ('113', 'ljw-222', 'ljw-222', '100.00', '20150119095319', 'deposit', '301.00', '401.00');
INSERT INTO `detail` VALUES ('114', 'ljw-222', 'ljw-222', '1000.00', '20150119100842', 'deposit', '401.00', '1401.00');
INSERT INTO `detail` VALUES ('115', 'ljw-222', 'ljw-222', '20.00', '20150119100859', 'deposit', '1401.00', '1421.00');
INSERT INTO `detail` VALUES ('116', 'ljw-222', 'ljw-222', '2000.00', '20150119101619', 'deposit', '1421.00', '3421.00');
INSERT INTO `detail` VALUES ('117', 'ljw-222', 'ljw-222', '100.00', '20150119102915', 'draw', '3421.00', '3321.00');
INSERT INTO `detail` VALUES ('118', 'ljw-222', 'ljw-222', '200.00', '20150119103018', 'draw', '3321.00', '3121.00');
INSERT INTO `detail` VALUES ('119', 'ljw-222', 'ljw-222', '2000.00', '20150119103038', 'draw', '3121.00', '1121.00');
INSERT INTO `detail` VALUES ('120', 'ljw-222', 'ljw-222', '10.00', '20150119103045', 'draw', '1121.00', '1111.00');
INSERT INTO `detail` VALUES ('121', 'ljw-222', 'ljw-222', '100.00', '20150119103446', 'draw', '1111.00', '1011.00');
INSERT INTO `detail` VALUES ('122', 'ljw-222', 'ljw-222', '200.00', '20150120091426', 'deposit', '1011.00', '1211.00');
INSERT INTO `detail` VALUES ('123', 'ljw-222', 'ljw-222', '200.00', '20150120091439', 'draw', '1211.00', '1011.00');
INSERT INTO `detail` VALUES ('124', 'ljw-222', 'ljw-222', '11.00', '20150120091553', 'draw', '1011.00', '1000.00');
INSERT INTO `detail` VALUES ('125', 'ljw-222', 'ljw-111', '100.00', '20150120094458', 'transOut', '1000.00', '900.00');
INSERT INTO `detail` VALUES ('126', 'ljw-111', 'ljw-222', '100.00', '20150120094458', 'transIn', '1.00', '101.00');
INSERT INTO `detail` VALUES ('127', 'ljw-222', 'ljw-111', '100.00', '20150120094509', 'transOut', '900.00', '800.00');
INSERT INTO `detail` VALUES ('128', 'ljw-111', 'ljw-222', '100.00', '20150120094509', 'transIn', '101.00', '201.00');
INSERT INTO `detail` VALUES ('129', 'ljw-222', 'ljw-111', '100.00', '20150120094723', 'transOut', '800.00', '700.00');
INSERT INTO `detail` VALUES ('130', 'ljw-111', 'ljw-222', '100.00', '20150120094723', 'transIn', '201.00', '301.00');
INSERT INTO `detail` VALUES ('131', 'ljw-222', 'ljw-111', '100.00', '20150120095303', 'transOut', '700.00', '600.00');
INSERT INTO `detail` VALUES ('132', 'ljw-111', 'ljw-222', '100.00', '20150120095303', 'transIn', '301.00', '401.00');
INSERT INTO `detail` VALUES ('133', 'ljw-222', 'ljw-222', '100.00', '20150120110534', 'draw', '600.00', '500.00');
INSERT INTO `detail` VALUES ('134', 'ljw-222', 'ljw-222', '400.00', '20150120114403', 'deposit', '500.00', '900.00');
INSERT INTO `detail` VALUES ('135', 'ljw-222', 'ljw-222', '200.00', '20150120114426', 'draw', '900.00', '700.00');
INSERT INTO `detail` VALUES ('136', '2011210586-20150111061327', 'ljw-222', '100.00', '20150120024246', 'transOut', '35091.00', '34991.00');
INSERT INTO `detail` VALUES ('137', 'ljw-222', '2011210586-20150111061327', '100.00', '20150120024246', 'transIn', '700.00', '800.00');
INSERT INTO `detail` VALUES ('138', 'ljw-222', 'ljw-111', '100.00', '20150120024402', 'transOut', '800.00', '700.00');
INSERT INTO `detail` VALUES ('139', 'ljw-111', 'ljw-222', '100.00', '20150120024402', 'transIn', '401.00', '501.00');
INSERT INTO `detail` VALUES ('140', 'ljw-222', 'ljw-222', '100.00', '20150120050432', 'deposit', '700.00', '800.00');
INSERT INTO `detail` VALUES ('141', 'ljw-222', 'ljw-222', '100.00', '20150120050745', 'deposit', '800.00', '900.00');
INSERT INTO `detail` VALUES ('142', 'ljw-222', 'ljw-222', '1000.00', '20150120051002', 'deposit', '900.00', '1900.00');
INSERT INTO `detail` VALUES ('143', 'ljw-222', 'ljw-222', '500.00', '20150120051603', 'draw', '1900.00', '1400.00');
INSERT INTO `detail` VALUES ('144', 'ljw-222', 'ljw-111', '100.00', '20150120051815', 'transOut', '1400.00', '1300.00');
INSERT INTO `detail` VALUES ('145', 'ljw-111', 'ljw-222', '100.00', '20150120051815', 'transIn', '501.00', '601.00');
INSERT INTO `detail` VALUES ('146', 'ljw-222', 'ljw-111', '100.00', '20150120051830', 'transOut', '1300.00', '1200.00');
INSERT INTO `detail` VALUES ('147', 'ljw-111', 'ljw-222', '100.00', '20150120051830', 'transIn', '601.00', '701.00');
INSERT INTO `detail` VALUES ('148', 'ljw-222', 'ljw-222', '1000.00', '20150121104013', 'deposit', '1200.00', '2200.00');
INSERT INTO `detail` VALUES ('149', 'ljw-222', 'ljw-222', '200.00', '20150121104023', 'draw', '2200.00', '2000.00');
INSERT INTO `detail` VALUES ('150', 'ljw-222', 'ljw-222', '100.00', '20150121105018', 'draw', '2000.00', '1900.00');
INSERT INTO `detail` VALUES ('151', 'ljw-222', 'ljw-222', '200.00', '20150121105146', 'draw', '1900.00', '1700.00');
INSERT INTO `detail` VALUES ('152', 'ljw-222', 'ljw-222', '100.00', '20150121105426', 'draw', '1700.00', '1600.00');
INSERT INTO `detail` VALUES ('153', 'ljw-222', 'ljw-111', '300.00', '20150121110105', 'transOut', '1600.00', '1300.00');
INSERT INTO `detail` VALUES ('154', 'ljw-111', 'ljw-222', '300.00', '20150121110105', 'transIn', '701.00', '1001.00');
INSERT INTO `detail` VALUES ('155', 'tvxq-20150121031053', 'tvxq-20150121031053', '10000.00', '20150121031435', 'deposit', '1.00', '10001.00');
INSERT INTO `detail` VALUES ('156', 'tvxq-20150121031053', 'tvxq-20150121031053', '2000.00', '20150121031458', 'draw', '10001.00', '8001.00');
INSERT INTO `detail` VALUES ('157', 'ljw-222', 'ljw-111', '100.00', '20150121031645', 'transOut', '1300.00', '1200.00');
INSERT INTO `detail` VALUES ('158', 'ljw-111', 'ljw-222', '100.00', '20150121031645', 'transIn', '1001.00', '1101.00');
INSERT INTO `detail` VALUES ('159', 'tvxq-20150121031053', 'ljw-222', '1000.00', '20150121031705', 'transOut', '8001.00', '7001.00');
INSERT INTO `detail` VALUES ('160', 'ljw-222', 'tvxq-20150121031053', '1000.00', '20150121031705', 'transIn', '1200.00', '2200.00');
INSERT INTO `detail` VALUES ('161', 'tvxq-20150121031053', 'ljw-222', '100.00', '20150121031957', 'transOut', '7001.00', '6901.00');
INSERT INTO `detail` VALUES ('162', 'ljw-222', 'tvxq-20150121031053', '100.00', '20150121031957', 'transIn', '2200.00', '2300.00');
INSERT INTO `detail` VALUES ('163', '000-20150121052017', '000-20150121052017', '10000.00', '20150121052256', 'deposit', '1.00', '10001.00');
INSERT INTO `detail` VALUES ('164', '000-20150121052017', '000-20150121052017', '2000.00', '20150121052324', 'draw', '10001.00', '8001.00');
INSERT INTO `detail` VALUES ('165', '000-20150121052017', 'ljw-222', '1000.00', '20150121052402', 'transOut', '8001.00', '7001.00');
INSERT INTO `detail` VALUES ('166', 'ljw-222', '000-20150121052017', '1000.00', '20150121052402', 'transIn', '2300.00', '3300.00');
INSERT INTO `detail` VALUES ('167', '222-20150122034752', '222-20150122034752', '10000.00', '20150122035402', 'deposit', '1.00', '10001.00');
INSERT INTO `detail` VALUES ('168', '222-20150122034752', '222-20150122034752', '200.00', '20150122035433', 'draw', '10001.00', '9801.00');
INSERT INTO `detail` VALUES ('169', '222-20150122034752', 'ljw-222', '100.00', '20150122035600', 'transOut', '9801.00', '9701.00');
INSERT INTO `detail` VALUES ('170', 'ljw-222', '222-20150122034752', '100.00', '20150122035600', 'transIn', '3300.00', '3400.00');
INSERT INTO `detail` VALUES ('171', 'pp-20150122043351', 'pp-20150122043351', '10000.00', '20150122045409', 'deposit', '1.00', '10001.00');
INSERT INTO `detail` VALUES ('172', 'pp-20150122043351', 'pp-20150122043351', '2000.00', '20150122045434', 'draw', '10001.00', '8001.00');
INSERT INTO `detail` VALUES ('173', 'pp-20150122043351', 'ljw-222', '1000.00', '20150122045520', 'transOut', '8001.00', '7001.00');
INSERT INTO `detail` VALUES ('174', 'ljw-222', 'pp-20150122043351', '1000.00', '20150122045520', 'transIn', '3400.00', '4400.00');
INSERT INTO `detail` VALUES ('175', 'ljw-222', 'ljw-222', '100.00', '20150123093221', 'deposit', '4400.00', '4500.00');
INSERT INTO `detail` VALUES ('176', 'ljw-222', 'ljw-222', '2000.00', '20150123093245', 'draw', '4500.00', '2500.00');
INSERT INTO `detail` VALUES ('177', 'ljw-222', 'ljw-111', '1000.00', '20150123093523', 'transOut', '2500.00', '1500.00');
INSERT INTO `detail` VALUES ('178', 'ljw-111', 'ljw-222', '1000.00', '20150123093523', 'transIn', '1101.00', '2101.00');
