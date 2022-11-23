## javaagent原理
    参考： javaagent原理.png
## 使用场景
    性能监控探针： SkyWalking等
## 步骤：
#### 1、入口类中编写如下两个方法，作为探针引入其他工程之后,会在jvm使用工程字节码时，
    使用premain方法对字节码进行代理，类似于JVM级别的AOP
    public static void premain(String agentArgs, Instrumentation inst);
    public static void premain(String agentArgs);
    参考：PreMainAgent.java
    
#### 2、使用maven插件，将启动类信息和方法写入到MANIFEST.MF中
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <appendAssemblyId>false</appendAssemblyId>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <!--自动添加META-INF/MANIFEST.MF -->
                        <manifest>
                            <addClasspath>true</addClasspath>
                        </manifest>
                        <manifestEntries>
                            <Premain-Class>com.cz.agent.PreMainAgent</Premain-Class>
                            <Agent-Class>com.cz.agent.PreMainAgent</Agent-Class>
                            <Can-Redefine-Classes>true</Can-Redefine-Classes>
                            <Can-Retransform-Classes>true</Can-Retransform-Classes>
                        </manifestEntries>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    
####3、使用maven install 插件 打包
####4、获取到打好的探针jar包路径(例如: /user/java_agent.jar)， 在需要使用javaagent工程中，
        添加JVM启动参数: -javaagent:/user/java_agent.jar
        也可以添加探针参数:  -javaagent:/user/java_agent.jar=xxxxx    xxxxx由探针入口类的第一个String类型参数接收
    
    
    