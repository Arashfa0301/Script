module script.springboot.server {
    requires com.fasterxml.jackson.databind;

    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.security.web;
    requires spring.boot.autoconfigure;

    requires script.core.main;
    requires script.data;

    requires com.google.gson;

    opens springboot.server
            to spring.beans, spring.boot, spring.security, spring.context, spring.web, spring.core;
}
