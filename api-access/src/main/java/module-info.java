open module apiaccess {
    requires gson;
    requires retrofit2;
    requires retrofit2.converter.gson;

    requires spring.context;
    requires spring.beans;

    requires slf4j.api;

//    requires com.google.common;
    requires crypto;
    requires transitive datamodel;

    exports com.yfy.beem.clientv3.apiaccess;
}