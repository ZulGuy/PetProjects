package com.example.editor.interfaces;

public interface Visitable {

  String accept(SqlVisitor visitor);

}
