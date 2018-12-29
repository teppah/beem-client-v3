open module datamodel {
    requires slf4j.api;
    requires transitive crypto;

    exports com.yfy.beem.clientv3.datamodel;
}