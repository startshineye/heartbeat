#HeartBeat
<div>
    <p>  test 
     心跳检测各类应用服务器(如Tomcat,Jetty),WEB服务器(如 Apache,Nginx) 的JAVA WEB应用程序.
    </p>
    <p>
     如何实现?
     <br/>
     使用HttpClient对指定的服务器(application-instance) URL 按频率(10秒,20秒...) 发起请求并记录响应的信息(连接耗时,是否连接成功,是否有异常,响应数据包大小),
     若检测到不正常(响应码不是200,抛出异常...)时则发送邮件给指定的地址,当检测恢复正常时也发送提醒邮件.
     <br/>
     将来会添加更多的实时提醒方式接口,如微信,短信
    </p>
</div>

<div>
    <h3>使用的框架及版本</h3>
    <ul>
        <li>JDK - 1.7</li>
        <li>Spring Framework - 3.2.2.RELEASE</li>
        <li>Quartz - 2.2.1</li>
        <li>Hibernate - 4.1.7.Final</li>
        <li>HttpClient - 4.3.5</li>
        <li><a href="http://www.bootcss.com/p/flat-ui/">Flat UI</a></li>
        <li>Maven - 3.3.9</li>
        <li>TestNG - 6.9.10 (Unit Test)</li>
    </ul>
</div>

<div>
    <h3>特点</h3>
    <ul>
        <li>无侵入,独立部署</li>
        <li>可同时监测多个应用服务器</li>
        <li>请求方式支持GET,POST; URL支持http与https, 可指定请求content-type, 添加请求参数(固定参数或随机参数)</li>
        <li>添加安全设置,可控制用户注册,设定用户权限等</li>
        <li>使用简洁,灵活</li>
        <li>提醒方式及时,多样(目前仅实现邮件提醒,将来会加入微信提醒,短信提醒等)</li>
    </ul>
</div>

<div>
    <h3>运行环境</h3>
    <ul>
        <li>JRE 1.7 +</li>
        <li>MySql 5.5 +</li>
        <li>Tomcat 7 +</li>
    </ul>
</div>

<div>
    <h3>开发环境</h3>
    <ul>
        <li>IntelliJ IDEA 2016.2 +</li>
    </ul>
</div>

<div>
    <h3>在线测试</h3>
    <a href="http://lib.iegreen.net/hb/">iegreen HeartBeat</a>
</div>

<div>
    <h3>如何使用?</h3>
    <ol>
        <li>项目是Maven管理的, 需要在电脑上安装maven(开发用的版本号为3.3.9), MySql(开发用的版本号为5.6)</li>
        <li>下载(或clone)项目到本地</li>
        <li>
            创建MySQL数据库(默认数据库名:heart_beat), 并运行相应的SQL脚本(脚本文件位于others/database目录),
            <br/>
            运行脚本的顺序: HeartBeat.ddl -> quartz_mysql_innodb.sql
        </li>
        <li>
            修改HeartBeat.properties(位于src/main/resources目录)中的数据库连接信息(包括username, password等)
            <br/>
            <strong>NOTE: 为了确保能收到提醒邮件,请将配置文件中的 <em>mail.develop.address</em> 配置为你的邮件地址;
            若在生产环境,请将 <em>mail.develop.environment</em> 值修改为 false (true表示为开发环境)</strong>
        </li>
        <li>
            将本地项目导入到IDE(如Intellij IDEA)中,配置Tomcat(或类似的servlet运行服务器), 并启动Tomcat(默认端口为8080)
            <br/>
            <br/>
               另: 也可通过maven package命令将项目编译为war文件(HeartBeat.war),
                     将war放在Tomcat中并启动(注意: 这种方式需要将HeartBeat.properties加入到classpath中并正确配置数据库连接信息).
                     <br/>
                     或直接在项目的'dist'目录下载完整版安装包.
        </li>
    </ol>

    <h3>如何通过微信接收监控信息</h3>
    <ol>
        <li>通过微信扫描二维码 <img src="http://lib.iegreen.net/hb/resources/images/iegreen-wx.jpg"/> 或添加公众号 iegreen;
        在关注后绑定在 <a href="http://lib.iegreen.net/hb/">http://lib.iegreen.net/hb/</a> 中注册的账号(一个账号可以绑定多个微信号)</li>
        <li>在添加或修改Instance 时勾选上需要接收监控信息的微信号并保存; 待有心跳检测不正常时发提示信息到微信</li>
    </ol>

</div>



<div>
    <h3>邮件配置说明</h3>
    <p>
        HeartBeat项目使用的邮件服务器使用SSL连接, 所以在配置邮件(javaMailSender, HeartBeat.xml文件)时, 使用了SSL连接配置,包括<code>mail.smtp.auth</code>与<code>mail.smtp.socketFactory.class</code>;
        <br/>
        若在使用中配置邮件后不工作, 请检查配置(如使用的邮件服务器是否支持SSL)并编写单元测试来测试邮件发送能正常工作(项目的邮件单元测试在 MailTransmitterTest.java 文件中,
        记得将测试的emailAddress设置为自己邮箱).
        <br/>
        <strong>另:  强烈建议使用SSL连接邮件服务器</strong>
    </p>
    <p>
        在项目的配置文件<code>HeartBeat.properties</code>中, 可配置邮件为开发环境或生产环境,具体参数为<code>mail.develop.environment</code>
        与<code>mail.develop.address</code>, 若将<code>mail.develop.environment</code> = true为生产环境, false为开发环境; 开发环境时的邮件只为
        发给<code>mail.develop.address</code>配置的邮箱,不会发给真正的邮件接收者; 生产环境时<code>mail.develop.address</code>配置不启作用.
    </p>
</div>


<hr/>
<br/>


<div>
    <h3>程序运行主要截图</h3>
    <ol>
        <li>
            <p>
                Monitoring
                <br/>
                <img src="http://andaily.qiniudn.com/hbmonitoring_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Instance - Monitoring details
                <br/>
                <img src="http://andaily.qiniudn.com/hbmonitoring-details_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Instance - Overview
                <br/>
                <img src="http://andaily.qiniudn.com/hbinstances_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Instance - Create
                <br/>
                <img src="http://andaily.qiniudn.com/hbnew-instance_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Monitoring-Log
                <br/>
                <img src="http://andaily.qiniudn.com/hbhb-log_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Monitoring-Reminder-Log
                <br/>
                <img src="http://andaily.qiniudn.com/hbreminder-log_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
        <li>
            <p>
                Search
                <br/>
                <img src="http://andaily.qiniudn.com/hbsearch_0.3.png" alt="hb"/>
                <br/>
            </p>
        </li>
    </ol>
</div>


<hr/>
<div>
    More Open-Source projects see <a href="https://team.oschina.net/iegreen/">https://team.oschina.net/iegreen/</a>
    <br/>
    From <a href="http://iegreen.net">iegreen.net</a>
    <br/>
    Email <a href="mailto:fq@iegreen.net">fq@iegreen.net</a>
</div>
<p>
    <img src="http://lib.iegreen.net/hb/resources/images/iegreen-wx.jpg"/>
</p>