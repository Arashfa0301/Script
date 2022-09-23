module script.data {

    requires transitive script.core.main;
    requires com.google.gson;

    exports data;

    opens data;
}