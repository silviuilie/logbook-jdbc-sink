package org.pm.zalando.logbook;


import org.junit.platform.suite.api.*;
import org.pm.zalando.logbook.jdbc.JdbcBatchSinkIntegrationTest;
import org.pm.zalando.logbook.jdbc.SimpleJdbcSinkIntegrationTest;
import org.pm.zalando.logbook.jdbc.SimpleJdbcSinkTest;
import org.pm.zalando.logbook.jdbc.SimpleJdbcSinkWithDetailedFormatterTest;

@Suite
@SuiteDisplayName("runs all")
@SelectClasses({
        JdbcBatchSinkIntegrationTest.class,
        SimpleJdbcSinkIntegrationTest.class,
        SimpleJdbcSinkTest.class,
        SimpleJdbcSinkWithDetailedFormatterTest.class
})
public class RunAll {
    //
}

