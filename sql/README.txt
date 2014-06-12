1)Mysql must have next configuration

username="root" 
password="root" 
driverClassName="com.mysql.jdbc.Driver"
url="jdbc:mysql://localhost:3306/bookmaker"

2)For creation and fills tables you must execute 
  - init tables.sql
  - fill tables.sql
  
3)For creatin pool of connection add next strings to context.xml

<Resource name="jdbc/BM" 
              auth="Container" 
              type="javax.sql.DataSource" 
              factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
              defaultAutoCommit="false"
              testWhileIdle="true"
              testOnBorrow="true"
              testOnReturn="false"
              validationQuery="SELECT 1"
              validationInterval="30000"
              timeBetweenEvictionRunsMillis="30000"
              maxActive="100" 
              minIdle="10" 
              maxWait="10000" 
              initialSize="10"
              removeAbandonedTimeout="60"
              removeAbandoned="true"
              logAbandoned="true"
              minEvictableIdleTimeMillis="30000" 
              jmxEnabled="true"
              jdbcInterceptors=
"org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer"
              username="root" 
              password="root" 
              driverClassName="com.mysql.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/bookmaker"/>
              
If you want deploy project on server you must:
a)In Constants.java edit and change app name and host
b)edit *.js files and change app name
c)edit addTrial.js, editTrial.js, bets.js
d)edit web.xml