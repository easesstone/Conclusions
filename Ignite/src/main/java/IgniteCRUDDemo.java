import org.apache.ignite.configuration.CacheConfiguration;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;


/**
 * iginte 要点.md
 * 首先把基础知识点中的内容弄清楚
 * https://www.zybuluo.com/liyuj/note/963460#138%E7%AC%AC%E4%B8%80%E4%B8%AA%E6%9C%8D%E5%8A%A1%E7%BD%91%E6%A0%BC%E5%BA%94%E7%94%A8
 */
public class IgniteCRUDDemo {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String jdbcUrl = "jdbc:ignite:thin://172.18.18.105/";
        Connection conn = getIgniteConnection(jdbcUrl);
        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setSqlFunctionClasses(MyCompareFunction.class);
//        createTabelDemo(conn);
//        createDataForCityTable(conn);
//        createDataForPersonTable(conn);
//        getTableDataV1(conn);
//        getTableData(conn);
//        System.out.println("==========================");
//        getTabelDataV2(conn);
//        getTabelDataV3(conn);
//        insertIntoIgnite(conn);
//        getTableDataDemo(conn);
        getMyDefineFunction(conn);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getMyDefineFunction(Connection connection) {
        PreparedStatement preparedStatement = null;
        String featureStr = "0.42622453:-2.466152:4.5122013:6.990057:6.4356475:-4.5288186:-0.40542394:2.6007192:1.2853222:-1.6261744:1.2775128:2.1323936:1.1684783:4.6512585:0.6441663:2.578511:1.5231955:6.4208016:0.35449415:1.1628689:0.42125988:-0.28766382:-0.6250566:0.68809134:0.25739267:3.6042047:-1.8363893:2.7292943:3.7065156:0.5459016:-1.1836784:2.7608223:-0.19982924:2.3724992:2.111054:-6.45857:-2.0606668:2.0884326:0.5533567:1.7571541:-3.522346:3.5647414:1.5438926:-1.234139:0.79096884:-0.5893211:-2.810445:3.9587429:0.7666898:1.8382754:0.2590086:-0.2714439:2.9506466:-1.6126814:3.1474168:-0.15500419:1.7774723:2.9760787:-2.2429786:1.7111282:-0.6127775:1.5256879:-4.230491:-2.0872743:0.44928977:-0.97829443:-0.11757484:-0.32743552:0.008860841:0.45189822:-1.0955157:-0.42754555:-0.04062678:2.3693905:-1.6897289:-2.909631:0.70853925:0.67000335:-1.7440784:-2.7628977:-1.5396304:-0.24872744:-0.41726473:0.17498472:-0.42317364:-1.3072785:1.5609274:0.3830578:3.735248:-1.7887443:1.6458637:-1.2832677:-0.97513974:-1.6153928:-2.707716:1.55768:-1.1891408:2.751004:-3.529587:5.090732:-3.315076:-1.3234096:-1.3347782:1.2402047:-2.9874873:-2.1946707:2.010122:-1.3565774:2.4698312:3.3042026:-4.0036826:-1.7440184:-1.8707261:2.5981932:1.4100125:-3.5582492:1.8260396:-1.4460278:-2.4253795:-0.11460215:0.1609285:2.6501005:1.6480806:-2.3650036:-0.96425563:-2.2744157:-0.8434308:3.4185584:2.0723014:-1.672218:-0.14827372:3.4261305:2.0934794:1.5462737:-2.045914:0.7278797:-2.318004:-2.750248:3.577189:-4.6146235:1.054688:-1.8787855:1.8192885:0.36838886:-0.22989284:-1.6702746:-1.2882096:-3.6436322:-1.4514656:1.5857702:-0.05320379:-0.6563498:0.0769382:2.608165:-0.5008712:-3.3683002:-1.5080612:-3.6841474:1.6140077:-3.1981227:0.24384622:0.5121302:0.95626926:-2.1510465:1.2677459:-1.0400345:0.086885914:2.2618601:3.4861069:2.674602:-0.4824113:-4.291851:-1.6836036:4.045406:-5.5451875:0.08494105:-1.7189752:0.5324318:1.4175079:0.42720187:-2.4292712:2.987577:-3.8696785:-2.4104269:2.329752:-1.3192754:-0.827885:-0.57566416:-0.497447:-0.14885771:-3.148648:3.4121237:-1.6511908:0.03795153:2.10503:-0.22548324:0.98686796:0.7939403:2.875456:1.6176676:-1.5021731:2.6380749:2.3598495:1.8329263:2.4170341:0.23253733:-2.2114635:3.7775276:-1.4625456:-0.38814583:-0.78076255:2.5588489:1.0407448:-1.8460187:-0.4290508:-0.3242409:1.3139105:1.8265802:5.3624096:0.9230108:1.5036149:2.1240683:-3.9885776:-0.67544377:0.5158225:-2.4921713:-0.19130546:2.905454:-2.4824028:-1.8109591:0.17385253:-0.1168887:-1.4164767:-3.1192203:0.28562272:-1.114325:-3.0804422:-0.90480983:3.3729076:-0.9398133:3.0131636:3.7079244:1.9681792:1.5457473:4.0929317:-1.1966811:-1.3777001:-1.0911108:-2.1028764:-4.229354:-0.17740685:-8.538623:-4.4899716:-1.3605564:-2.5289762:-0.21344605:0.72028136:-1.7978904:3.5505903:5.3080378:-1.198249:-0.6113864:0.45779082:-0.8943883:-0.42374033:-1.663758:1.4144347:-0.80776024:1.4682057:-1.7508789:0.6299628:1.2912527:-1.5766572:-1.7594434:-1.8499062:4.489204:-0.7585645:-1.0952581:2.650465:-0.58775985:-0.4816853:-3.997574:4.755275:1.3855903:-2.8730605:-1.3038554:-4.483663:1.9895347:-1.131011:-4.436141:-1.4050784:1.5002534:5.5238023:6.74894:1.7882292:-2.3556898:-0.75349504:-1.7985042:-3.3844724:0.35930854:4.691123:3.4583764:3.1422415:-1.8120587:-2.269219:-1.9210453:-0.42554224:0.43395653:-2.2922938:-1.5973598:-1.4268837:-1.6795572:0.99078727:2.269702:0.48329228:1.1126813:0.11759594:-1.2119468:3.6808963:2.5286071:-0.9501004:-1.4752367:0.806309:4.1941338:-0.27761754:3.6047688:-0.30498624:1.3652265:-2.6272144:-4.6511:-1.3113972:0.28406602:1.4175893:-1.7368722:2.4275572:2.719882:-1.5101094:0.15396841:-1.0087372:2.7621489:-1.8024008:2.6543324:0.9070564:3.53735:1.2712873:-2.5905137:-0.927201:0.06728509:1.0408349:0.59117603:-4.005251:0.7503282:4.896223:-3.0886557:-2.1786983:-3.1226826:4.584035:-1.5641137:3.220982:-0.17764616:4.0115533:-2.7125106:-4.4729233:0.65710557:-1.2155011:-0.12557015:0.9545376:-1.0253522:4.8073626:-1.9848613:-2.053698:-2.2696025:2.3877382:-1.3342392:0.27587095:-1.9079609:-0.2890053:1.5350525:0.36045584:-2.0724397:0.20764235:-0.48474967:1.9675506:0.048462927:-6.273697:0.36628458:0.7646109:2.5224295:-0.2640211:3.8180985:3.9733777:-4.30697:1.8680828:2.692854:-0.66182524:0.70244855:-0.18313181:-0.53890866:-1.0132611:-2.0440261:3.8157594:-1.5028533:-1.1481072:-0.65500873:-0.89325505:4.020369:4.387177:1.1612642:-1.7888609:-2.3973408:-0.08168568:0.7231259:-2.6269138:-0.28695413:0.23542899:-0.3844145:5.709496:0.9700458:1.8095782:-0.11023161:-2.0935354:2.9224086:-1.5828233:-4.1033883:-2.649647:1.8418653:-0.54926896:-2.6664305:-2.1904507:1.1418318:-3.5536375:-1.4073598:-4.3304467:-0.77457196:-1.3360294:-0.7354594:3.858086:-0.35809433:1.2454138:1.4568557:0.70598036:3.175993:-1.238254:1.5469701:-3.3185472:0.28815284:2.1719484:-4.4671097:1.8030995:-1.4627461:-0.9773836:-3.8435779:1.1059657:-0.32147804:-3.079029:-3.3095274:-0.45237663:-0.0371027:2.0218067:4.822349:4.44127:0.39339957:-2.6644754:0.66319174:1.6838585:1.2544426:1.1740677:-3.8008924:-2.8528926:2.3921316:0.8420967:-1.9152308:-0.07384005:4.7902136:-1.2418357:-2.6526597:-0.0010479093:3.9560766:0.16566336:2.5258074:-1.2108383:-0.75386953:-3.3737416:-0.63592434:4.6501656:-0.3262775:1.9465088:-0.94616467:-2.201607:-2.813021:-3.6743217:-2.310878:0.6112778:-1.4282033:-0.8712742:5.397695:0.77836573:1.0816188:-5.2573695:-3.018939:-7.203055:0.3879935:2.301549:-3.0201159:-0.32111308:1.1401418:-2.7939308:-2.6068738:-2.187756:-3.3696964:-2.3401008:1.5304128";
        float[] feature = string2floatArray(featureStr);
        String sql = "select id,featureCompare(?, feature) as sim from picture_person";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, feature);
            ResultSet rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getTableDataDemo(Connection conn) throws IOException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM picture_person");
            while (rs.next()){
                byte[] photo = rs.getBytes(8);
                float[] feature = (float[]) rs.getObject(9);
                for (int i = 0; i < feature.length; i ++) {
                    System.out.println(feature[i]);
                }
                Image2Byte2Image.byte2image(photo, "D:\\" + UUID.randomUUID().toString() + ".jpg");
                System.out.println(rs.getString(1) +
                        ", " + rs.getString(2) +
                        ", " + rs.getString(3) +
                        ", " + rs.getString(4) +
                        ", " + rs.getString(5) +
                        ", " + rs.getString(6) +
                        ", " + rs.getString(7) +
                        ", " + rs.getString(10) +
                        ", " + rs.getString(11) +
                        ", " + rs.getString(12) +
                        ", " + rs.getString(13) +
                        ", " + rs.getString(14) +
                        ", " + rs.getString(15) +
                        ", " + rs.getString(16));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 将特征值（String）转换为特征值（float[]）（内）（赵喆）
     *
     * @param feature 传入编码为UTF-8的String
     * @return 返回float[]类型的特征值
     */
    public static float[] string2floatArray(String feature) {
        if (feature != null && feature.length() > 0) {
            float[] featureFloat = new float[512];
            String[] strArr = feature.split(":");
            for (int i = 0; i < strArr.length; i++) {
                featureFloat[i] = Float.valueOf(strArr[i]);
            }

            return featureFloat;
        }
        return new float[0];
    }

    public static void insertIntoIgnite(Connection connection) throws IOException {
        String sql = "insert into picture_person(id,name,platformid,tag,pkey,idcard,sex,photo,feature,reason,creator," +
                "cphone,createtime,updatetime,important,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
            pstm.setString(2, "nameValue");
            pstm.setString(3, "platformidValue");
            pstm.setString(4, "tagValue");
            pstm.setString(5, "pkeyValue");
            pstm.setString(6, "idcardValue");
            pstm.setInt(7, 1);
            pstm.setBytes(8, Image2Byte2Image.image2byte(ClassLoader.getSystemResource("13012119831219002X.jpg").getPath()));
            String featureStr = "0.42622453:-2.466152:4.5122013:6.990057:6.4356475:-4.5288186:-0.40542394:2.6007192:1.2853222:-1.6261744:1.2775128:2.1323936:1.1684783:4.6512585:0.6441663:2.578511:1.5231955:6.4208016:0.35449415:1.1628689:0.42125988:-0.28766382:-0.6250566:0.68809134:0.25739267:3.6042047:-1.8363893:2.7292943:3.7065156:0.5459016:-1.1836784:2.7608223:-0.19982924:2.3724992:2.111054:-6.45857:-2.0606668:2.0884326:0.5533567:1.7571541:-3.522346:3.5647414:1.5438926:-1.234139:0.79096884:-0.5893211:-2.810445:3.9587429:0.7666898:1.8382754:0.2590086:-0.2714439:2.9506466:-1.6126814:3.1474168:-0.15500419:1.7774723:2.9760787:-2.2429786:1.7111282:-0.6127775:1.5256879:-4.230491:-2.0872743:0.44928977:-0.97829443:-0.11757484:-0.32743552:0.008860841:0.45189822:-1.0955157:-0.42754555:-0.04062678:2.3693905:-1.6897289:-2.909631:0.70853925:0.67000335:-1.7440784:-2.7628977:-1.5396304:-0.24872744:-0.41726473:0.17498472:-0.42317364:-1.3072785:1.5609274:0.3830578:3.735248:-1.7887443:1.6458637:-1.2832677:-0.97513974:-1.6153928:-2.707716:1.55768:-1.1891408:2.751004:-3.529587:5.090732:-3.315076:-1.3234096:-1.3347782:1.2402047:-2.9874873:-2.1946707:2.010122:-1.3565774:2.4698312:3.3042026:-4.0036826:-1.7440184:-1.8707261:2.5981932:1.4100125:-3.5582492:1.8260396:-1.4460278:-2.4253795:-0.11460215:0.1609285:2.6501005:1.6480806:-2.3650036:-0.96425563:-2.2744157:-0.8434308:3.4185584:2.0723014:-1.672218:-0.14827372:3.4261305:2.0934794:1.5462737:-2.045914:0.7278797:-2.318004:-2.750248:3.577189:-4.6146235:1.054688:-1.8787855:1.8192885:0.36838886:-0.22989284:-1.6702746:-1.2882096:-3.6436322:-1.4514656:1.5857702:-0.05320379:-0.6563498:0.0769382:2.608165:-0.5008712:-3.3683002:-1.5080612:-3.6841474:1.6140077:-3.1981227:0.24384622:0.5121302:0.95626926:-2.1510465:1.2677459:-1.0400345:0.086885914:2.2618601:3.4861069:2.674602:-0.4824113:-4.291851:-1.6836036:4.045406:-5.5451875:0.08494105:-1.7189752:0.5324318:1.4175079:0.42720187:-2.4292712:2.987577:-3.8696785:-2.4104269:2.329752:-1.3192754:-0.827885:-0.57566416:-0.497447:-0.14885771:-3.148648:3.4121237:-1.6511908:0.03795153:2.10503:-0.22548324:0.98686796:0.7939403:2.875456:1.6176676:-1.5021731:2.6380749:2.3598495:1.8329263:2.4170341:0.23253733:-2.2114635:3.7775276:-1.4625456:-0.38814583:-0.78076255:2.5588489:1.0407448:-1.8460187:-0.4290508:-0.3242409:1.3139105:1.8265802:5.3624096:0.9230108:1.5036149:2.1240683:-3.9885776:-0.67544377:0.5158225:-2.4921713:-0.19130546:2.905454:-2.4824028:-1.8109591:0.17385253:-0.1168887:-1.4164767:-3.1192203:0.28562272:-1.114325:-3.0804422:-0.90480983:3.3729076:-0.9398133:3.0131636:3.7079244:1.9681792:1.5457473:4.0929317:-1.1966811:-1.3777001:-1.0911108:-2.1028764:-4.229354:-0.17740685:-8.538623:-4.4899716:-1.3605564:-2.5289762:-0.21344605:0.72028136:-1.7978904:3.5505903:5.3080378:-1.198249:-0.6113864:0.45779082:-0.8943883:-0.42374033:-1.663758:1.4144347:-0.80776024:1.4682057:-1.7508789:0.6299628:1.2912527:-1.5766572:-1.7594434:-1.8499062:4.489204:-0.7585645:-1.0952581:2.650465:-0.58775985:-0.4816853:-3.997574:4.755275:1.3855903:-2.8730605:-1.3038554:-4.483663:1.9895347:-1.131011:-4.436141:-1.4050784:1.5002534:5.5238023:6.74894:1.7882292:-2.3556898:-0.75349504:-1.7985042:-3.3844724:0.35930854:4.691123:3.4583764:3.1422415:-1.8120587:-2.269219:-1.9210453:-0.42554224:0.43395653:-2.2922938:-1.5973598:-1.4268837:-1.6795572:0.99078727:2.269702:0.48329228:1.1126813:0.11759594:-1.2119468:3.6808963:2.5286071:-0.9501004:-1.4752367:0.806309:4.1941338:-0.27761754:3.6047688:-0.30498624:1.3652265:-2.6272144:-4.6511:-1.3113972:0.28406602:1.4175893:-1.7368722:2.4275572:2.719882:-1.5101094:0.15396841:-1.0087372:2.7621489:-1.8024008:2.6543324:0.9070564:3.53735:1.2712873:-2.5905137:-0.927201:0.06728509:1.0408349:0.59117603:-4.005251:0.7503282:4.896223:-3.0886557:-2.1786983:-3.1226826:4.584035:-1.5641137:3.220982:-0.17764616:4.0115533:-2.7125106:-4.4729233:0.65710557:-1.2155011:-0.12557015:0.9545376:-1.0253522:4.8073626:-1.9848613:-2.053698:-2.2696025:2.3877382:-1.3342392:0.27587095:-1.9079609:-0.2890053:1.5350525:0.36045584:-2.0724397:0.20764235:-0.48474967:1.9675506:0.048462927:-6.273697:0.36628458:0.7646109:2.5224295:-0.2640211:3.8180985:3.9733777:-4.30697:1.8680828:2.692854:-0.66182524:0.70244855:-0.18313181:-0.53890866:-1.0132611:-2.0440261:3.8157594:-1.5028533:-1.1481072:-0.65500873:-0.89325505:4.020369:4.387177:1.1612642:-1.7888609:-2.3973408:-0.08168568:0.7231259:-2.6269138:-0.28695413:0.23542899:-0.3844145:5.709496:0.9700458:1.8095782:-0.11023161:-2.0935354:2.9224086:-1.5828233:-4.1033883:-2.649647:1.8418653:-0.54926896:-2.6664305:-2.1904507:1.1418318:-3.5536375:-1.4073598:-4.3304467:-0.77457196:-1.3360294:-0.7354594:3.858086:-0.35809433:1.2454138:1.4568557:0.70598036:3.175993:-1.238254:1.5469701:-3.3185472:0.28815284:2.1719484:-4.4671097:1.8030995:-1.4627461:-0.9773836:-3.8435779:1.1059657:-0.32147804:-3.079029:-3.3095274:-0.45237663:-0.0371027:2.0218067:4.822349:4.44127:0.39339957:-2.6644754:0.66319174:1.6838585:1.2544426:1.1740677:-3.8008924:-2.8528926:2.3921316:0.8420967:-1.9152308:-0.07384005:4.7902136:-1.2418357:-2.6526597:-0.0010479093:3.9560766:0.16566336:2.5258074:-1.2108383:-0.75386953:-3.3737416:-0.63592434:4.6501656:-0.3262775:1.9465088:-0.94616467:-2.201607:-2.813021:-3.6743217:-2.310878:0.6112778:-1.4282033:-0.8712742:5.397695:0.77836573:1.0816188:-5.2573695:-3.018939:-7.203055:0.3879935:2.301549:-3.0201159:-0.32111308:1.1401418:-2.7939308:-2.6068738:-2.187756:-3.3696964:-2.3401008:1.5304128";
            float[] feature = string2floatArray(featureStr);
            pstm.setObject(9, feature);
            pstm.setString(10, "reasonValue");
            pstm.setString(11, "creatorValue");
            pstm.setString(12, "cphoneValue");
            pstm.setTimestamp(13, new java.sql.Timestamp(System.currentTimeMillis()));
            pstm.setTimestamp(14, new java.sql.Timestamp(System.currentTimeMillis()));
            pstm.setInt(15, 0);
            pstm.setInt(16, 1);
            pstm.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 右连接
    public static void getTabelDataV3(Connection connection) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select p.*, c.* from Person p right join City c on p.city_id = c.id");
            while (rs.next())
                System.out.println(rs.getString(1) + ", " + rs.getString(2)
                        + ", " + rs.getString(3) + ", " + rs.getString(4) + "," + rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 左连接
    public static void getTabelDataV2(Connection connection) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select p.*, c.* from Person p left join City c on p.city_id = c.id");
            while (rs.next())
                System.out.println(rs.getString(1) + ", " + rs.getString(2)
                        + ", " + rs.getString(3) + ", " + rs.getString(4) + "," + rs.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 内连接
    public static void getTableDataV1(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.*, c.* FROM Person p inner join City c on p.city_id = c.id");
            while (rs.next())
                System.out.println(rs.getString(1) + ", " + rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 和内连接情况相同
    public static void getTableData(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.name, c.name " +
                            " FROM Person p, City c " +
                            " WHERE p.city_id = c.id");
             while (rs.next())
             System.out.println(rs.getString(1) + ", " + rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDataForPersonTable(Connection conn) {
        try{
            PreparedStatement stmt =
                    conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)");
//            stmt.setLong(1, 1L);
//            stmt.setString(2, "John Doe");
//            stmt.setLong(3, 3L);
//            stmt.executeUpdate();
//            stmt.setLong(1, 2L);
//            stmt.setString(2, "Jane Roe");
//            stmt.setLong(3, 2L);
//            stmt.executeUpdate();
//            stmt.setLong(1, 3L);
//            stmt.setString(2, "Mary Major");
//            stmt.setLong(3, 1L);
//            stmt.executeUpdate();
//            stmt.setLong(1, 4L);
//            stmt.setString(2, "Richard Miles");
//            stmt.setLong(3, 2L);
//            stmt.executeUpdate();

            PreparedStatement stmtV2 =
                    conn.prepareStatement("INSERT INTO Person (id, name) VALUES (?, ?)");
            stmtV2.setLong(1, 5L);
            stmtV2.setString(2, "Richard Miles");
            stmtV2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDataForCityTable(Connection conn) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)");
//            stmt.setLong(1, 1L);
//            stmt.setString(2, "Forest Hill");
//            stmt.executeUpdate();
//            stmt.setLong(1, 2L);
//            stmt.setString(2, "Denver");
//            stmt.executeUpdate();
//            stmt.setLong(1, 3L);
//            stmt.setString(2, "St. Petersburg");
//            stmt.executeUpdate();
            stmt.setLong(1, 4L);
            stmt.setString(2, "HangZhou");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getIgniteConnection(String jdbcUrl) {
        try {
            Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
            try {
                return DriverManager.getConnection(jdbcUrl);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void createTabelDemo(Connection conn){
        try {
            System.out.printf("connection is : " + conn);
            Statement stmt = conn.createStatement();
            // Create database tables
            // Create table based on REPLICATED templatee
            stmt.executeUpdate("create table picture_person(id char(32) not null primary key," +
                    "name varchar," +
                    "platformid varchar," +
                    "tag varchar," +
                    "pkey varchar," +
                    "idcard varchar," +
                    "sex int," +
                    "photo binary," +
                    "feature array," +
                    "reason varchar," +
                    "creator varchar," +
                    "cphone varchar," +
                    "createtime timestamp," +
                    "updatetime timestamp," +
                    "important int," +
                    "status int)");
//            stmt.executeUpdate("CREATE TABLE City (" +
//                    " id LONG PRIMARY KEY, name VARCHAR) " +
//                    " WITH \"template=replicated\"");
//            // Create table based on PARTITIONED template with one backup.
//            stmt.executeUpdate("CREATE TABLE Person (" +
//                    " id LONG, name VARCHAR, city_id LONG, " +
//                    " PRIMARY KEY (id, city_id)) " +
//                    " WITH \"backups=1, affinityKey=city_id\"");
//            // Create an index on the City table.
//            stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
//            // Create an index on the Person table.
//            stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

