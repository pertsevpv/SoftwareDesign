package ru.akirakozov.sd.refactoring.servlet;

import java.io.PrintWriter;
import java.io.StringWriter;

class TestWriter extends PrintWriter {

  public TestWriter() {
    super(new StringWriter());
  }

  @Override
  public String toString() {
    return out.toString();
  }
}
