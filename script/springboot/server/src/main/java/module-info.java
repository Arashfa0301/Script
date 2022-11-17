module script.springboot.server {

  requires spring.web;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.core;
  requires spring.boot.autoconfigure;

  requires script.core.main;
  requires script.data;

  opens springboot.server to spring.beans, spring.context, spring.web, spring.core;
}
