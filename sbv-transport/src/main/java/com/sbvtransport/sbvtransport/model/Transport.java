package com.sbvtransport.sbvtransport.model;

import static javax.persistence.GenerationType.IDENTITY;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;


@MappedSuperclass
public class Transport implements Serializable {

  // TODO: Add current location.

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "line", referencedColumnName = "id")
  protected Line line;

  @Column(name = "late", unique = false, nullable = false)
  protected boolean late;

  @Column(name = "name", unique = false, nullable = false)
  protected String name;

  public Transport() {

  }

  public Transport(Line line, boolean late, String name) {
    this.line = line;
    this.late = late;
    this.name = name;
  }

  public Line getLine() {
    return line;
  }

  public void setLine(Line line) {
    this.line = line;
  }

  public Long getId() {
    return id;
  }

  public boolean isLate() {
    return late;
  }

  public String getName() {
    return name;
  }

  public void setLate(boolean late) {
    this.late = late;
  }

  public void setName(String name) {
    this.name = name;
  }

}
