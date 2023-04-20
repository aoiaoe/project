package com.geekbang.principle.isp.bad;

import java.util.Collection;

/**
 * 可能不符合接口隔离
 * 分场景：
 *  1、如果每次调用count()方法，而调用方只需要其中的一些返回值，则不符合
 *  2、如果每次调用count()方法，调用法需要返回值的所有指标，则是符合原则的
 *
 *
 *  如果在项目中，对每个统计需求，Statistics 定义的那几个统计信息都有涉及，那 count()
 * 函数的设计就是合理的。相反，如果每个统计需求只涉及 Statistics 罗列的统计信息中一部
 * 分，比如，有的只需要用到 max、min、average 这三类统计信息，有的只需要用到
 * average、sum。而 count() 函数每次都会把所有的统计信息计算一遍，就会做很多无用
 * 功，势必影响代码的性能，特别是在需要统计的数据量很大的时候。所以，在这个应用场景
 * 下，count() 函数的设计就有点不合理了，我们应该按照第二种设计思路，将其拆分成粒度
 * 更细的多个统计函数。
 */
public class Statistics {
    private Long max;
    private Long min;
    private Long average;
    private Long sum;
    private Long percentile99;
    private Long percentile999;
//... 省略 constructor/getter/setter 等方法...

    public Statistics count(Collection<Long> dataSet) {
        Statistics statistics = new Statistics();
//... 省略计算逻辑...
        return statistics;
    }
}