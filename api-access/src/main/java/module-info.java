open module apiaccess {
    requires gson;
    requires retrofit2;
    requires retrofit2.converter.gson;

    requires spring.context;
    requires spring.beans;

    requires datamodel;

    exports com.yfy.beem.clientv3.apiaccess;
}