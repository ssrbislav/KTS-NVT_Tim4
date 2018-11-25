package com.sbvtransport.sbvtransport.dto;

public class SubwayDTO {

  private boolean late;
  private String name;
  private Long id_line;

  public SubwayDTO(){

  }

  public SubwayDTO(boolean late, String name, Long id_line) {
    super();
    this.late = late;
    this.name = name;
    this.id_line = id_line;
  }


  public boolean isLate() {
    return late;
  }

  public void setLate(boolean late) {
    this.late = late;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId_line() {
    return id_line;
  }

  public void setId_line(Long id_line) {
    this.id_line = id_line;
  }



}
