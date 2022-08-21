### XML
        接口接受XML数据，并相应XML数据
        
        此项目中使用的方式为引入XML所需要的的依赖
        <dependency>
            <groupId>com.fasterxml.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-xml</artifactId>
            <version>2.8.8</version>
        </dependency>
        
        
        例如: 
        DemoController.java中测试接口中注明了,消费XML数据,生成XML数据
        @GetMapping(value = "/data", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
        
        在实体类的属性上加上需要从XML中映射的属性, 如Data.java
        @JacksonXmlRootElement(localName ="message")
        public class Data {
        
            @JacksonXmlProperty(localName = "name")
            private String name;
        
            @JacksonXmlProperty(localName = "id")
            private Long id;
        }
        
        接口注明了消费XML数据,这时我传入的参数为:
           <message>
               <name>Test数据</name>
               <id>25</id>
           </message>
        参数解析器就会将Test数据映射到Data实体的name字段上, id同理
        
        
        接口标注返回的是一个XML数据,就会将Data实体些为一个XML返回
