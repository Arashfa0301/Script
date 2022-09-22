module script.data {
    requires transitive script.core.main;

    requires com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;

    exports data;

    opens data;
}