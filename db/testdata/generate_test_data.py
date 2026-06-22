#!/usr/bin/env python3
"""
华水校园生活服务平台 · 测试数据生成脚本
生成真实感测试数据，覆盖全部 29 张表
"""
import random
import datetime
import json
from collections import defaultdict

random.seed(42)

# ============================================================
# 配置
# ============================================================
OUTPUT_FILE = "03_huashui_campus_life_test_data.sql"
NOW = datetime.datetime(2026, 6, 22, 10, 0, 0)

# BCrypt hash for password "123456" (all test users use same password)
BCRYPT_PASSWORD = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5Eh"

# ============================================================
# 华水实际学院列表
# ============================================================
COLLEGES = [
    (1,  "水利学院",              "SL",  1),
    (2,  "地球科学与工程学院",    "DK",  1),
    (3,  "测绘与地理信息学院",    "CH",  1),
    (4,  "材料学院",              "CL",  1),
    (5,  "土木与交通学院",        "TM",  1),
    (6,  "电力学院",              "DL",  1),
    (7,  "机械学院",              "JX",  1),
    (8,  "环境与市政工程学院",    "HJ",  2),
    (9,  "水资源学院",            "SZ",  1),
    (10, "管理与经济学院",        "GJ",  1),
    (11, "数学与统计学院",        "ST",  1),
    (12, "建筑学院",              "JZ",  2),
    (13, "信息工程学院",          "XX",  1),
    (14, "电子工程学院",          "DZ",  1),
    (15, "外国语学院",            "WY",  1),
    (16, "法学院",                "FX",  1),
    (17, "公共管理学院",          "GG",  1),
    (18, "国际教育学院",          "GJXY",1),
    (19, "艺术学院",              "YS",  1),
    (20, "马克思主义学院",        "MKS", 1),
    (21, "乌拉尔学院",            "WEL", 1),
]

# 姓氏库
SURNAMES = "王李张刘陈杨赵黄周吴徐孙马胡朱郭何罗高林郑梁谢唐许冯宋韩邓彭曹曾田萧潘袁蔡蒋余于杜叶程苏魏吕丁任卢姚沈钟姜崔谭陆范汪廖石金贾夏韦付方白邹孟熊秦邱江尹薛闫段雷侯龙史陶黎贺顾毛郝龚邵万钱严覃武戴莫孔向汤"

# 名字库
MALE_NAMES = [
    "浩宇","子轩","浩然","子涵","宇轩","子墨","一鸣","宇航","嘉懿","博文",
    "思远","天宇","俊杰","文博","致远","志远","明哲","伟诚","鸿飞","承志",
    "博涛","兴邦","泽宇","瑞霖","昊然","昊天","浩天","泽洋","鑫鹏",
    "俊豪","智宸","烨霖","晟睿","文昊","修洁","黎昕","远航","旭尧",
    "鸿涛","伟祺","荣轩","越泽","浩宸","瑾瑜","皓轩","擎苍","擎宇","志泽",
    "睿渊","弘文","哲瀚","雨泽","楷瑞","建辉","晋鹏","天磊","绍辉",
    "泽楷","伟宸","靖琪","益弘","旭鹏","靖宸","泽宇","振豪","烨伟","鸿煊",
]

FEMALE_NAMES = [
    "雨桐","梓涵","诗涵","梦瑶","思琪","语嫣","晓雪","雨萱","美琳","欣怡",
    "静怡","雨欣","雅琪","文静","诗雨","思雨","倩倩","雪婷","佳怡","雨菲",
    "梦洁","晓婷","玉婷","嘉欣","梦琪","晓燕","秀英","慧敏","婷婷","雪梅",
    "丽华","秀兰","桂英","秀珍","美玲","丽萍","秀芳","秀云","丽丽","文婷",
    "思颖","婉婷","若溪","熙然","依诺","舒涵","以沫","璟雯","子萱","昕怡",
    "心怡","雨嘉","悦然","梓萱","若瑶","沐兮","雨薇","子琪","羽彤","依晨",
    "雪瑶","梓怡","玥婷","安妮","可馨","乐瑶","亦菲","紫萱","语嫣","若曦",
]

# 保洁员名
CLEANER_NAMES_M = ["张建国","李保国","王志强","刘有才","陈大山","赵为民"]
CLEANER_NAMES_F = ["王秀英","李美兰","张玉芬","刘翠华","陈红梅","赵丽萍"]

# 宿管名
DORM_MANAGER_NAMES = ["周明远","吴建华","郑国栋","孙永强","钱立军","何志宏"]


def gen_phone():
    prefixes = ["138","139","150","151","152","158","159","186","187","188","135","136","137","155","156","157","183","185","189"]
    return random.choice(prefixes) + "".join(str(random.randint(0,9)) for _ in range(8))


def ts(dt):
    """datetime to SQL string"""
    return dt.strftime("%Y-%m-%d %H:%M:%S")


def ds(dt):
    """datetime to date string"""
    return dt.strftime("%Y-%m-%d")


def esc(s):
    """escape single quotes"""
    return s.replace("'", "''")


# ============================================================
# 主生成逻辑
# ============================================================
def generate():
    lines = []
    w = lines.append

    w("-- " + "="*65)
    w("-- 华水校园生活服务平台 · 测试数据（自动生成）")
    w(f"-- 生成时间: {ts(NOW)}")
    w("-- 说明: 用于开发测试，数据力求真实")
    w("-- " + "="*65)
    w("")
    w("USE `huashui_campus_life`;")
    w("")
    w("SET NAMES utf8mb4;")
    w("")

    # ========================================================
    # 1. 学院数据 21条
    # ========================================================
    w("-- ============================================================")
    w("-- 一、学院数据 (21条)")
    w("-- ============================================================")
    for cid, name, code, campus_id in COLLEGES:
        w(f"INSERT INTO `sys_college` (`id`, `college_name`, `college_code`, `campus_id`, `sort_order`, `status`) "
          f"VALUES ({cid}, '{esc(name)}', '{code}', {campus_id}, {cid}, 1);")
    w("")

    # ========================================================
    # 2. 用户数据
    # ========================================================
    w("-- ============================================================")
    w("-- 二、用户数据")
    w("-- ============================================================")

    users = []
    uid = 1
    used_phone = set()
    used_uname = set()

    # 超级管理员
    users.append({"id":uid,"username":"admin","real_name":"系统管理员","phone":"13800000000","email":"admin@ncwu.edu.cn","gender":1,"user_type":"SUPER_ADMIN","campus_id":None,"college_id":None,"major":None,"grade":None})
    uid += 1

    # 宿管 6名
    dm_ids = []
    dm_names_list = list(DORM_MANAGER_NAMES)
    for campus_id in [1,2,3]:
        for j in range(2):
            name = dm_names_list.pop(0) if dm_names_list else f"宿管{campus_id}0{j+1}"
            uname = f"dm{campus_id}0{j+1}"
            users.append({"id":uid,"username":uname,"real_name":name,"phone":gen_phone(),"email":None,"gender":1,"user_type":"DORM_MANAGER","campus_id":campus_id,"college_id":None,"major":None,"grade":None})
            dm_ids.append(uid)
            uid += 1

    # 保洁 12名
    cleaner_ids = []
    cn_list = CLEANER_NAMES_M + CLEANER_NAMES_F
    random.shuffle(cn_list)
    for campus_id in [1,2,3]:
        for j in range(4):
            name = cn_list.pop(0) if cn_list else f"保洁{campus_id}0{j+1}"
            uname = f"cl{campus_id}0{j+1}"
            users.append({"id":uid,"username":uname,"real_name":name,"phone":gen_phone(),"email":None,"gender":1,"user_type":"CLEANER","campus_id":campus_id,"college_id":None,"major":None,"grade":None})
            cleaner_ids.append(uid)
            uid += 1

    # 学院按校区分配
    colleges_c1 = list(range(1,16))   # 1-15 龙子湖
    colleges_c2 = list(range(8,21))   # 8-20 花园（部分）
    colleges_c3 = [18,21,13,14,1]     # 江淮
    campus_colleges = {1: colleges_c1, 2: colleges_c2, 3: colleges_c3}

    MAJORS = {
        1:["水利水电工程","水文与水资源工程","港口航道与海岸工程"],
        2:["地质工程","地球物理学"],
        3:["测绘工程","遥感科学与技术"],
        4:["材料科学与工程"],
        5:["土木工程","交通工程"],
        6:["电气工程及其自动化","能源与动力工程"],
        7:["机械设计制造及其自动化","车辆工程"],
        8:["环境工程","给排水科学与工程"],
        9:["水文与水资源工程","水务工程"],
        10:["工程管理","会计学","国际经济与贸易"],
        11:["数学与应用数学","统计学"],
        12:["建筑学","城乡规划"],
        13:["计算机科学与技术","软件工程","人工智能"],
        14:["电子信息工程","通信工程"],
        15:["英语","翻译"],
        16:["法学","行政管理"],
        17:["行政管理","劳动与社会保障"],
        18:["土木工程(中外)","机械工程(中外)"],
        19:["环境设计","数字媒体艺术"],
        20:["思想政治教育"],
        21:["能源与动力工程(中外)","测绘工程(中外)"],
    }

    # 学生 80名
    grades = ["2022","2023","2024","2025"]
    grade_w = [0.15,0.25,0.35,0.25]
    campus_w = [0.60,0.25,0.15]

    student_ids_by_campus = defaultdict(list)
    used_sno = set()

    male_cids = {1,2,3,4,5,6,7,13,14,18,21}
    female_cids = {12,15,19}

    for _ in range(80):
        grade = random.choices(grades, weights=grade_w, k=1)[0]
        campus = random.choices([1,2,3], weights=campus_w, k=1)[0]
        clist = campus_colleges.get(campus, colleges_c1)
        cid = random.choice(clist)

        base = int(grade) * 100000 + cid * 1000
        sno = base
        attempt = 0
        while sno in used_sno and attempt < 999:
            attempt += 1
            sno = base + attempt
        used_sno.add(sno)
        uname = str(sno)

        if cid in male_cids:
            gender = random.choices([1,0], weights=[0.75,0.25], k=1)[0]
        elif cid in female_cids:
            gender = random.choices([1,0], weights=[0.25,0.75], k=1)[0]
        else:
            gender = random.choice([0,1])

        if gender == 1:
            rname = random.choice(SURNAMES) + random.choice(MALE_NAMES)
        else:
            rname = random.choice(SURNAMES) + random.choice(FEMALE_NAMES)

        major = random.choice(MAJORS.get(cid, ["未知专业"]))
        phone = gen_phone()
        email = f"{uname}@qq.com"

        users.append({"id":uid,"username":uname,"real_name":rname,"phone":phone,"email":email,"gender":gender,"user_type":"STUDENT","campus_id":campus,"college_id":cid,"major":major,"grade":grade})
        student_ids_by_campus[campus].append(uid)
        uid += 1

    # 输出用户
    for u in users:
        ph = f"'{u['phone']}'" if u['phone'] else "NULL"
        em = f"'{u['email']}'" if u['email'] else "NULL"
        ge = str(u['gender']) if u['gender'] is not None else "NULL"
        ca = str(u['campus_id']) if u['campus_id'] else "NULL"
        co = str(u['college_id']) if u['college_id'] else "NULL"
        ma = f"'{esc(u['major'])}'" if u['major'] else "NULL"
        gr = f"'{u['grade']}'" if u['grade'] else "NULL"
        w(f"INSERT INTO `sys_user` (`id`,`username`,`password`,`real_name`,`phone`,`email`,`gender`,`user_type`,`campus_id`,`college_id`,`major`,`grade`,`status`) "
          f"VALUES ({u['id']},'{u['username']}','{BCRYPT_PASSWORD}','{esc(u['real_name'])}',{ph},{em},{ge},'{u['user_type']}',{ca},{co},{ma},{gr},1);")
    w("")

    # 用户角色关联
    w("-- 用户-角色关联")
    role_map = {"STUDENT":1,"CLEANER":2,"DORM_MANAGER":3,"SUPER_ADMIN":4}
    for u in users:
        w(f"INSERT INTO `sys_user_role` (`user_id`,`role_id`) VALUES ({u['id']},{role_map[u['user_type']]});")
    w("")

    # ========================================================
    # 3. 房间与床位
    # ========================================================
    w("-- ============================================================")
    w("-- 三、房间与床位")
    w("-- ============================================================")

    # 选代表性楼栋
    # building_id -> (room_type, bed_count, campus_id, area)
    target_buildings = {
        1: ("FOUR",4,1), 2: ("FOUR",4,1),
        8: ("SIX",6,1), 10: ("SIX",6,1),
        24: ("FOUR",4,2), 25: ("SIX",6,2),
        30: ("SIX",6,2), 36: ("FOUR",4,3), 38: ("FOUR",4,3),
    }

    rooms_per_floor = 5
    total_floors = 6

    room_id = 1
    bed_id = 1
    all_rooms = []

    for bid, (rtype, bcnt, campus) in target_buildings.items():
        for fl in range(1, total_floors + 1):
            for rn in range(1, rooms_per_floor + 1):
                rnum = f"{fl}{rn:02d}"
                all_rooms.append({
                    "id":room_id, "building_id":bid, "room_number":rnum,
                    "floor_number":fl, "room_type":rtype,
                    "total_beds":bcnt, "campus_id":campus,
                })
                # 床位
                for bnum in range(1, bcnt+1):
                    w(f"INSERT INTO `dorm_bed` (`id`,`room_id`,`bed_number`,`status`) "
                      f"VALUES ({bed_id},{room_id},'{bnum}号床',0);")
                    bed_id += 1
                room_id += 1

    # 输出房间
    for r in all_rooms:
        w(f"INSERT INTO `dorm_room` (`id`,`building_id`,`room_number`,`floor_number`,`room_type`,`total_beds`,`occupied_beds`,`status`) "
          f"VALUES ({r['id']},{r['building_id']},'{r['room_number']}',{r['floor_number']},'{r['room_type']}',{r['total_beds']},0,1);")
    w("")

    # ========================================================
    # 4. 学生住宿分配
    # ========================================================
    w("-- ============================================================")
    w("-- 四、学生住宿分配")
    w("-- ============================================================")

    sr_id = 1
    # 按校区分配
    for campus in [1,2,3]:
        campus_students = student_ids_by_campus.get(campus, []).copy()
        random.shuffle(campus_students)
        # 该校区房间
        campus_rooms = [(r["id"],r["total_beds"],r["building_id"]) for r in all_rooms if r["campus_id"]==campus]
        random.shuffle(campus_rooms)

        room_assign = defaultdict(list)
        room_target = {}
        for rid,bcnt,bid in campus_rooms:
            if random.random()<0.15:
                room_target[rid]=0
            elif random.random()<0.10:
                room_target[rid]=max(1,bcnt-random.randint(1,2))
            else:
                room_target[rid]=bcnt

        for sid in campus_students:
            avail = [(rid,bcnt) for rid,bcnt in room_target.items() if len(room_assign[rid])<bcnt]
            if not avail:
                break
            rid,_ = random.choice(avail)
            room_assign[rid].append(sid)

        for rid, slist in room_assign.items():
            rinfo = next(r for r in all_rooms if r["id"]==rid)
            for i,sid in enumerate(slist):
                bnum = i+1
                w(f"UPDATE `dorm_bed` SET `student_id`={sid},`status`=1 "
                  f"WHERE `room_id`={rid} AND `bed_number`='{bnum}号床';")
                check_in = NOW - datetime.timedelta(days=random.randint(90,730))
                w(f"INSERT INTO `dorm_student_record` (`id`,`student_id`,`campus_id`,`building_id`,`room_id`,`bed_id`,`check_in_time`,`status`) "
                  f"SELECT {sr_id},{sid},{campus},{rinfo['building_id']},{rid},id,'{ts(check_in)}',1 "
                  f"FROM `dorm_bed` WHERE `room_id`={rid} AND `bed_number`='{bnum}号床';")
                sr_id += 1

    # 更新 occupied_beds
    for r in all_rooms:
        w(f"UPDATE `dorm_room` SET `occupied_beds`="
          f"(SELECT COUNT(*) FROM `dorm_bed` WHERE `room_id`={r['id']} AND `status`=1) "
          f"WHERE `id`={r['id']};")
    w("")

    # ========================================================
    # 5. 水电余额
    # ========================================================
    w("-- ============================================================")
    w("-- 五、水电余额")
    w("-- ============================================================")
    for r in all_rooms:
        if random.random()<0.08:
            wb = round(random.uniform(-20,0),2); ws=0
        else:
            wb = round(random.uniform(5,200),2); ws=1
        wfq = round(random.uniform(5,15),2)
        wtu = round(random.uniform(0,150),3)
        w(f"INSERT INTO `water_balance` (`room_id`,`balance`,`free_quota`,`total_usage`,`status`) "
          f"VALUES ({r['id']},{wb},{wfq},{wtu},{ws});")

        if random.random()<0.05:
            eb = round(random.uniform(-30,0),2); es=0
        else:
            eb = round(random.uniform(10,300),2); es=1
        efq = round(random.uniform(20,50),2)
        etu = round(random.uniform(0,500),3)
        w(f"INSERT INTO `electricity_balance` (`room_id`,`balance`,`free_quota`,`total_usage`,`status`) "
          f"VALUES ({r['id']},{eb},{efq},{etu},{es});")
    w("")

    # ========================================================
    # 6. 水电记录 近30天
    # ========================================================
    w("-- ============================================================")
    w("-- 六、水电使用记录（近30天）")
    w("-- ============================================================")

    wrid = 1; erid = 1
    for r in all_rooms:
        bbw = round(random.uniform(30,200),2)
        bbe = round(random.uniform(50,350),2)
        for d in range(30,0,-1):
            date = ds(NOW - datetime.timedelta(days=d))
            uw = round(random.uniform(0.05,0.4),3)
            pw = 3.90
            aw = round(uw*pw,2)
            baw = round(bbw-aw,2)
            w(f"INSERT INTO `water_record` (`id`,`room_id`,`record_date`,`usage_amount`,`unit_price`,`amount`,`balance_before`,`balance_after`) "
              f"VALUES ({wrid},{r['id']},'{date}',{uw},{pw:.4f},{aw},{bbw},{baw});")
            bbw = baw; wrid += 1

            ue = round(random.uniform(1.0,8.0),3)
            pe = 0.56
            ae = round(ue*pe,2)
            bae = round(bbe-ae,2)
            w(f"INSERT INTO `electricity_record` (`id`,`room_id`,`record_date`,`usage_amount`,`unit_price`,`amount`,`balance_before`,`balance_after`) "
              f"VALUES ({erid},{r['id']},'{date}',{ue},{pe:.4f},{ae},{bbe},{bae});")
            bbe = bae; erid += 1
    w("")

    # ========================================================
    # 7. 缴费订单
    # ========================================================
    w("-- ============================================================")
    w("-- 七、缴费订单")
    w("-- ============================================================")

    all_sids = [u["id"] for u in users if u["user_type"]=="STUDENT"]
    random.shuffle(all_sids)
    pay_id = 1
    pay_date = NOW - datetime.timedelta(days=1)
    ptypes = ["WATER","ELECTRICITY"]
    pmethods = ["WECHAT","ALIPAY"]
    amounts = [10,20,50,100,200]

    for sid in all_sids[:25]:
        pt = random.choice(ptypes)
        amt = random.choice(amounts)
        pm = random.choice(pmethods)
        ono = f"PAY{pay_date.strftime('%Y%m%d')}{pay_id:04d}"
        tid = f"WX_TEST_{random.randint(10000,99999)}"
        tss = ts(pay_date)
        w(f"INSERT INTO `payment_order` (`id`,`order_no`,`user_id`,`room_id`,`payment_type`,`amount`,`pay_method`,`status`,`transaction_id`,`paid_time`) "
          f"SELECT {pay_id},'{ono}',{sid},dsr.room_id,'{pt}',{amt},'{pm}','SUCCESS','{tid}','{tss}' "
          f"FROM `dorm_student_record` dsr WHERE dsr.student_id={sid} AND dsr.status=1 LIMIT 1;")
        pay_id += 1
        pay_date -= datetime.timedelta(days=random.randint(1,5))

    # 失败/待支付
    for sid in all_sids[25:30]:
        pt = random.choice(ptypes)
        amt = random.choice(amounts)
        pm = random.choice(pmethods)
        ono = f"PAY{NOW.strftime('%Y%m%d')}{pay_id:04d}"
        st = random.choice(["PENDING","FAILED"])
        w(f"INSERT INTO `payment_order` (`id`,`order_no`,`user_id`,`room_id`,`payment_type`,`amount`,`pay_method`,`status`) "
          f"SELECT {pay_id},'{ono}',{sid},dsr.room_id,'{pt}',{amt},'{pm}','{st}' "
          f"FROM `dorm_student_record` dsr WHERE dsr.student_id={sid} AND dsr.status=1 LIMIT 1;")
        pay_id += 1
    w("")

    # ========================================================
    # 8. 请假申请
    # ========================================================
    w("-- ============================================================")
    w("-- 八、请假申请")
    w("-- ============================================================")

    ltypes = ["PERSONAL","SICK","OTHER"]
    personal_reasons = [
        "家中长辈生病，需要回老家探望照顾几天。",
        "参加校外学科竞赛培训，需请假三天。",
        "家里有重要事务需要回去处理一下。",
        "父母来郑州，需要陪父母办理一些手续。",
        "参加驾考科目二考试，已预约无法改期。",
    ]
    sick_reasons = [
        "因感冒发烧，体温38.5度，需要休息治疗，附校医院证明。",
        "急性肠胃炎，已在校医院就诊，医嘱建议休息两天。",
        "牙齿发炎疼痛，预约了口腔医院根管治疗，需要外出就医。",
        "因运动受伤，脚踝扭伤，需要卧床静养。",
    ]

    leave_id = 1
    for sid in all_sids[:15]:
        lt = random.choice(ltypes)
        campus = next((u["campus_id"] for u in users if u["id"]==sid),1)

        if lt=="SICK":
            reason = random.choice(sick_reasons)
        elif lt=="PERSONAL":
            reason = random.choice(personal_reasons)
        else:
            reason = "因其他个人原因需要请假外出处理。"

        days_ago = random.randint(1,60)
        start = NOW - datetime.timedelta(days=days_ago)
        end = start + datetime.timedelta(days=random.randint(1,5))

        status = random.choices(["PENDING","APPROVED","REJECTED"],weights=[0.3,0.55,0.15],k=1)[0]

        dm_campus = [u["id"] for u in users if u["user_type"]=="DORM_MANAGER" and u["campus_id"]==campus]
        approver = random.choice(dm_campus) if dm_campus else "NULL"

        approver_sql = "NULL"
        atime_sql = "NULL"
        op_sql = "NULL"
        reject_sql = "NULL"

        if status == "APPROVED":
            approver_sql = str(approver)
            a_dt = start + datetime.timedelta(hours=random.randint(1,24))
            atime_sql = f"'{ts(a_dt)}'"
            op_sql = "'同意请假，注意安全，按时返校。'"
        elif status == "REJECTED":
            approver_sql = str(approver)
            a_dt = start + datetime.timedelta(hours=random.randint(1,24))
            atime_sql = f"'{ts(a_dt)}'"
            reject_sql = "'请假理由不够充分，请补充相关证明材料后重新提交。'"

        w(f"INSERT INTO `leave_request` (`id`,`applicant_id`,`applicant_type`,`leave_type`,`campus_id`,`start_time`,`end_time`,`reason`,`status`,`approver_id`,`approve_time`,`approve_opinion`,`reject_reason`) "
          f"VALUES ({leave_id},{sid},'STUDENT','{lt}',{campus},'{ts(start)}','{ts(end)}','{reason}','{status}',{approver_sql},{atime_sql},{op_sql},{reject_sql});")
        leave_id += 1

    # 保洁请假
    for cid in cleaner_ids[:4]:
        lt = random.choice(ltypes)
        campus = next((u["campus_id"] for u in users if u["id"]==cid),1)
        days_ago = random.randint(5,30)
        start = NOW - datetime.timedelta(days=days_ago)
        end = start + datetime.timedelta(days=random.randint(1,3))
        dm_campus = [u["id"] for u in users if u["user_type"]=="DORM_MANAGER" and u["campus_id"]==campus]
        approver = random.choice(dm_campus) if dm_campus else 1
        a_dt = start + datetime.timedelta(hours=random.randint(2,12))
        w(f"INSERT INTO `leave_request` (`id`,`applicant_id`,`applicant_type`,`leave_type`,`campus_id`,`start_time`,`end_time`,`reason`,`status`,`approver_id`,`approve_time`,`approve_opinion`) "
          f"VALUES ({leave_id},{cid},'CLEANER','{lt}',{campus},'{ts(start)}','{ts(end)}','因个人原因需要请假{random.randint(1,3)}天，工作已与同事协调。','APPROVED',{approver},'{ts(a_dt)}','同意请假。');")
        leave_id += 1
    w("")

    # ========================================================
    # 9. 报修工单
    # ========================================================
    w("-- ============================================================")
    w("-- 九、报修工单")
    w("-- ============================================================")

    rtypes = ["PIPE","LIGHT","LOCK","AC","NETWORK","DOOR_WINDOW","FURNITURE","HEATING","OTHER"]
    rdesc = {
        "PIPE":"洗手池水龙头漏水，关不紧，一直滴水。",
        "LIGHT":"宿舍日光灯不亮了，更换了启辉器也不管用。",
        "LOCK":"宿舍门锁坏了，钥匙插进去拧不动。",
        "AC":"空调不制冷，开了半小时还是没冷风出来。",
        "NETWORK":"宿舍网线接口没信号，电脑插上网线显示未连接。",
        "DOOR_WINDOW":"窗户的把手断了，关不上窗户，冬天漏风。",
        "FURNITURE":"床的梯子螺丝松动了，上下铺有点晃。",
        "HEATING":"暖气片有一半不热，怀疑有气堵。",
        "OTHER":"卫生间排风扇坏了，不会转了。",
    }
    rnames = ["李师傅","王师傅","张师傅","赵师傅","刘师傅"]

    for i in range(20):
        sid = random.choice(all_sids)
        rt = random.choice(rtypes)
        campus = next((u["campus_id"] for u in users if u["id"]==sid),1)
        days_ago = random.randint(0,60)
        ono = f"REP{(NOW-datetime.timedelta(days=days_ago)).strftime('%Y%m%d')}{i+1:04d}"

        if days_ago<1:
            status = random.choices(["PENDING","ASSIGNED"],weights=[0.6,0.4])[0]
        elif days_ago<3:
            status = random.choices(["ASSIGNED","REPAIRING","COMPLETED"],weights=[0.3,0.4,0.3])[0]
        else:
            status = random.choices(["COMPLETED","EVALUATED"],weights=[0.4,0.6])[0]

        rpr = "NULL"
        rating = "NULL"
        if status in ("ASSIGNED","REPAIRING","COMPLETED","EVALUATED"):
            rpr = f"'{random.choice(rnames)}'"
        if status=="EVALUATED":
            rating = str(random.randint(3,5))

        w(f"INSERT INTO `repair_order` (`id`,`order_no`,`student_id`,`campus_id`,`building_id`,`room_id`,`repair_type`,`description`,`contact_phone`,`status`,`repairer_name`,`rating`) "
          f"SELECT {i+1},'{ono}',{sid},{campus},dsr.building_id,dsr.room_id,'{rt}','{rdesc[rt]}',u.phone,'{status}',{rpr},{rating} "
          f"FROM `dorm_student_record` dsr JOIN `sys_user` u ON u.id={sid} "
          f"WHERE dsr.student_id={sid} AND dsr.status=1 LIMIT 1;")
    w("")

    # ========================================================
    # 10. 考勤记录 近30天
    # ========================================================
    w("-- ============================================================")
    w("-- 十、考勤记录（近30天）")
    w("-- ============================================================")

    att_id = 1
    campus_buildings_map = {}
    for bid,(rtype,bcnt,campus) in target_buildings.items():
        campus_buildings_map.setdefault(campus,[]).append(bid)

    for cid in cleaner_ids:
        campus = next((u["campus_id"] for u in users if u["id"]==cid),1)
        bids = campus_buildings_map.get(campus, [1])
        bid = random.choice(bids)

        for d in range(30,0,-1):
            date_dt = NOW - datetime.timedelta(days=d)
            date_str = ds(date_dt)
            is_wknd = date_dt.weekday() >= 5

            if is_wknd and random.random()<0.5:
                status = "ABSENT"
                cit = "NULL"; ctyp = "NULL"; cloc = "NULL"
            else:
                rv = random.random()
                if rv<0.85:
                    status = "NORMAL"
                    hr = random.randint(6,8)
                    mi = random.randint(0,59)
                    cit = f"'{date_str} {hr:02d}:{mi:02d}:00'"
                    ctyp = f"'{random.choice(['GPS','PHOTO'])}'"
                    cloc = "'华北水利水电大学'"
                elif rv<0.95:
                    status = "LATE"
                    hr = random.randint(9,10)
                    mi = random.randint(0,59)
                    cit = f"'{date_str} {hr:02d}:{mi:02d}:00'"
                    ctyp = f"'{random.choice(['GPS','PHOTO'])}'"
                    cloc = "NULL"
                else:
                    status = "ABSENT"
                    cit = "NULL"; ctyp = "NULL"; cloc = "NULL"

            w(f"INSERT INTO `attendance_record` (`id`,`cleaner_id`,`campus_id`,`building_id`,`attendance_date`,`check_in_time`,`check_in_type`,`check_in_location`,`check_in_status`) "
              f"VALUES ({att_id},{cid},{campus},{bid},'{date_str}',{cit},{ctyp},{cloc},'{status}');")
            att_id += 1
    w("")

    # ========================================================
    # 11. 保洁任务
    # ========================================================
    w("-- ============================================================")
    w("-- 十一、保洁任务")
    w("-- ============================================================")

    task_id = 1
    task_contents = [
        "楼层走廊全面清洁","公共洗漱间深度保洁","楼梯扶手与台阶清洁",
        "楼栋大厅地板拖洗","垃圾桶清运与更换垃圾袋","走廊窗户与窗台擦拭",
        "公共卫生间消毒清洁","楼层墙壁污渍清理",
    ]

    for cid in cleaner_ids[:8]:
        campus = next((u["campus_id"] for u in users if u["id"]==cid),1)
        bids = campus_buildings_map.get(campus,[1])
        bid = random.choice(bids)
        dm_list = [u["id"] for u in users if u["user_type"]=="DORM_MANAGER" and u["campus_id"]==campus]
        assigner = random.choice(dm_list) if dm_list else 1

        for t in range(3):
            tn = f"{bid}号楼{random.randint(1,6)}层{random.choice(task_contents)}"
            days_ago = random.randint(0,14)
            dl_dt = NOW - datetime.timedelta(days=days_ago - random.randint(1,3))
            dl = ts(dl_dt.replace(hour=18,minute=0,second=0))
            completed = days_ago > 2
            status = "COMPLETED" if completed else random.choice(["PENDING","OVERDUE"])
            ct = f"'{ts(NOW - datetime.timedelta(days=days_ago))}'" if completed else "NULL"
            w(f"INSERT INTO `clean_task` (`id`,`task_name`,`cleaner_id`,`campus_id`,`building_id`,`area_desc`,`deadline`,`status`,`completed_time`,`assigner_id`) "
              f"VALUES ({task_id},'{tn}',{cid},{campus},{bid},'日常清洁区域','{dl}','{status}',{ct},{assigner});")
            task_id += 1
    w("")

    # ========================================================
    # 12. 保洁工作记录
    # ========================================================
    w("-- ============================================================")
    w("-- 十二、保洁工作记录")
    w("-- ============================================================")

    wl_id = 1
    for cid in cleaner_ids[:6]:
        campus = next((u["campus_id"] for u in users if u["id"]==cid),1)
        bids = campus_buildings_map.get(campus,[1])
        bid = random.choice(bids)
        for d in range(1,10):
            date = ds(NOW - datetime.timedelta(days=d))
            fl = random.randint(1,6)
            w(f"INSERT INTO `clean_work_log` (`id`,`cleaner_id`,`work_date`,`campus_id`,`building_id`,`area_desc`,`task_content`,`status`) "
              f"VALUES ({wl_id},{cid},'{date}',{campus},{bid},'{bid}号楼{fl}层走廊及公共区域','完成楼层走廊拖洗、垃圾桶清运、洗漱间台面擦拭','COMPLETED');")
            wl_id += 1
    w("")

    # ========================================================
    # 13. 评价数据
    # ========================================================
    w("-- ============================================================")
    w("-- 十三、评价配置与结果")
    w("-- ============================================================")

    dims = ["卫生状况","安全管理","设施维护","服务态度","管理规范"]
    ec_id = 1
    ed_id = 1

    for campus in [1,2,3]:
        dm_list = [u["id"] for u in users if u["user_type"]=="DORM_MANAGER" and u["campus_id"]==campus]
        creator = dm_list[0] if dm_list else 1

        start_t = NOW - datetime.timedelta(days=random.randint(30,90))
        end_t = start_t + datetime.timedelta(days=14)
        cname = {1:"龙子湖",2:"花园",3:"江淮"}[campus]
        config_name = f"2026年春季学期{cname}校区宿舍满意度调查"
        status = "CLOSED" if end_t<NOW else ("ACTIVE" if start_t<=NOW else "DRAFT")

        w(f"INSERT INTO `evaluation_config` (`id`,`config_name`,`campus_id`,`start_time`,`end_time`,`status`,`creator_id`) "
          f"VALUES ({ec_id},'{config_name}',{campus},'{ts(start_t)}','{ts(end_t)}','{status}',{creator});")

        for di,dn in enumerate(dims,1):
            w(f"INSERT INTO `evaluation_dimension` (`id`,`config_id`,`dimension_name`,`sort_order`) "
              f"VALUES ({ed_id},{ec_id},'{dn}',{di});")
            ed_id += 1

        if status=="CLOSED":
            cstus = student_ids_by_campus.get(campus,[])[:20]
            for si,sid in enumerate(cstus):
                rid = ec_id*1000+si+1
                sugg = random.choice(["整体满意，希望继续保持。","卫生还可以再加强一些。","宿管阿姨服务态度很好！","设施维修响应比较及时。",""])
                sub_t = start_t + datetime.timedelta(days=random.randint(1,12))
                w(f"INSERT INTO `evaluation_result` (`id`,`config_id`,`student_id`,`campus_id`,`building_id`,`room_id`,`suggestion`,`submit_time`) "
                  f"SELECT {rid},{ec_id},{sid},{campus},dsr.building_id,dsr.room_id,'{sugg}','{ts(sub_t)}' "
                  f"FROM `dorm_student_record` dsr WHERE dsr.student_id={sid} AND dsr.status=1 LIMIT 1;")

                base_did = ec_id*10-4  # first dim id = ec_id*10+1-5 = ec_id*10-4
                for di in range(5):
                    score = random.randint(3,5)
                    w(f"INSERT INTO `evaluation_score` (`result_id`,`dimension_id`,`dimension_name`,`score`) "
                      f"VALUES ({rid},{base_did+di},'{dims[di]}',{score});")
        ec_id += 1
    w("")

    # ========================================================
    # 14. 系统公告
    # ========================================================
    w("-- ============================================================")
    w("-- 十四、系统公告")
    w("-- ============================================================")

    notices = [
        ("关于龙子湖校区一区停水通知","各位同学：因水泵房检修，龙子湖校区一区将于6月25日8:00-12:00暂停供水，请提前做好储水准备。带来不便敬请谅解。","WATER_STOP",[1]),
        ("关于开展宿舍安全卫生检查的通知","按照学校统一部署，定于6月28日对三校区学生宿舍进行安全卫生检查，请各位同学提前整理好内务，严禁使用违规电器。","SAFETY_CHECK",None),
        ("关于暑假期间宿舍管理安排的通知","2026年暑假期间（7月10日-8月28日），学校将对各校区宿舍进行集中维修，留校学生需提前在系统内提交留宿申请。","HOLIDAY",None),
        ("关于花园校区北苑临时停电通知","接供电局通知，花园校区北苑将于7月1日14:00-16:00临时停电进行线路检修，请北苑同学提前做好准备。","POWER_STOP",[30,31,32,33,34,35]),
        ("关于2026年空调租赁续费的通知","2026-2027学年空调租赁续费已开始，请需要续租的同学在7月15日前通过本平台完成缴费，逾期将停止空调使用。","AC_RENTAL",None),
        ("江淮校区善水书院启用通知","江淮校区善水书院即日起正式向全体师生开放，提供自习室、活动室、心理驿站等服务，开放时间：每日8:00-22:00。","OTHER",None),
    ]

    for ni,(title,content,ntype,target) in enumerate(notices,1):
        pub_t = ts(NOW - datetime.timedelta(days=random.randint(1,20)))
        istop = 1 if ni<=2 else 0
        pscope = "CAMPUS" if target else "ALL"
        tj = f"'{json.dumps(target)}'" if target else "NULL"
        vc = random.randint(20,300)
        w(f"INSERT INTO `system_notice` (`id`,`title`,`content`,`notice_type`,`push_scope`,`target_campus_ids`,`is_top`,`publisher_id`,`publish_time`,`view_count`,`status`) "
          f"VALUES ({ni},'{esc(title)}','{esc(content)}','{ntype}','{pscope}',{tj},{istop},1,'{pub_t}',{vc},1);")
    w("")

    # ========================================================
    # 15. 公告已读记录
    # ========================================================
    w("-- ============================================================")
    w("-- 十五、公告已读记录")
    w("-- ============================================================")
    nrr_id = 1
    all_uids = [u["id"] for u in users]
    for ni in range(1,7):
        n_read = int(len(all_uids)*random.uniform(0.4,0.7))
        ruids = random.sample(all_uids, n_read)
        for uid in ruids[:30]:
            rt = ts(NOW - datetime.timedelta(hours=random.randint(1,200)))
            w(f"INSERT INTO `notice_read_record` (`id`,`notice_id`,`user_id`,`read_time`) "
              f"VALUES ({nrr_id},{ni},{uid},'{rt}');")
            nrr_id += 1
    w("")

    # ========================================================
    # 16. 操作日志
    # ========================================================
    w("-- ============================================================")
    w("-- 十六、操作日志")
    w("-- ============================================================")

    admin_ids = [u["id"] for u in users if u["user_type"]=="SUPER_ADMIN"]
    all_dm = [u for u in users if u["user_type"]=="DORM_MANAGER"]
    op_ids = admin_ids + [u["id"] for u in all_dm]

    logs = [
        ("CREATE","用户管理","创建新用户账号","sys_user"),
        ("UPDATE","宿舍管理","修改房间配置信息","dorm_room"),
        ("APPROVE","请假管理","审批请假申请","leave_request"),
        ("DELETE","用户管理","删除违规用户","sys_user"),
        ("UPDATE","宿舍管理","为学生分配床位","dorm_bed"),
        ("CREATE","系统管理","发布系统公告","system_notice"),
        ("APPROVE","报修管理","审批通过报修工单并派单","repair_order"),
        ("UPDATE","水电管理","对欠费房间执行停水","water_balance"),
        ("UPDATE","水电管理","恢复房间供水","water_balance"),
        ("CREATE","保洁管理","创建保洁任务","clean_task"),
    ]

    for i in range(30):
        ot,mod,desc,tt = random.choice(logs)
        oid = random.choice(op_ids)
        oname = next((u["real_name"] for u in users if u["id"]==oid),"未知")
        days_ago = random.randint(0,30)
        ct = ts(NOW - datetime.timedelta(days=days_ago,hours=random.randint(0,23)))
        tid = random.randint(1,100)
        w(f"INSERT INTO `operation_log` (`id`,`operator_id`,`operator_name`,`operation_type`,`operation_module`,`operation_desc`,`target_type`,`target_id`,`request_method`,`request_url`,`cost_time`,`status`) "
          f"VALUES ({i+1},{oid},'{esc(oname)}','{ot}','{mod}','{desc}','{tt}',{tid},'{random.choice(['POST','PUT','DELETE'])}','/api/{mod}/operate',{random.randint(50,500)},1);")
    w("")

    w("-- ============================================================")
    w(f"-- 测试数据生成完成！共 {len(lines)} 行 SQL")
    w("-- ============================================================")

    return "\n".join(lines)


if __name__ == "__main__":
    sql = generate()
    import os
    out_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "03_huashui_campus_life_test_data.sql")
    with open(out_path, "w", encoding="utf-8") as f:
        f.write(sql)
    print(f"Done: {out_path}")
    inserts = sum(1 for l in sql.split("\n") if l.strip().startswith("INSERT INTO"))
    updates = sum(1 for l in sql.split("\n") if l.strip().startswith("UPDATE"))
    print(f"  INSERT: {inserts}  UPDATE: {updates}  Size: {len(sql):,} chars")
