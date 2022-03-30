package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDTO {
  // 이메일
  private String uemail;
  // 암호
  private String upw;
  // 이름
  private String uname;
  // 전화번호
  private String uphone;
  // 권한
  private String urole;
  // 등록일
  private Date uregdate;
  // 날짜 포멧을 바꿔서 보관하기 위해
  private String uregdate1;

  // public MemberDTO() {
  // }

  // public MemberDTO(String uemail, String upw, String uname, String uphone,
  // String urole, Date uregdate,
  // String uregdate1) {
  // this.uemail = uemail;
  // this.upw = upw;
  // this.uname = uname;
  // this.uphone = uphone;
  // this.urole = urole;
  // this.uregdate = uregdate;
  // this.uregdate1 = uregdate1;
  // }

  // public String getUemail() {
  // return uemail;
  // }

  // public void setUemail(String uemail) {
  // this.uemail = uemail;
  // }

  // public String getUpw() {
  // return upw;
  // }

  // public void setUpw(String upw) {
  // this.upw = upw;
  // }

  // public String getUname() {
  // return uname;
  // }

  // public void setUname(String uname) {
  // this.uname = uname;
  // }

  // public String getUphone() {
  // return uphone;
  // }

  // public void setUphone(String uphone) {
  // this.uphone = uphone;
  // }

  // public String getUrole() {
  // return urole;
  // }

  // public void setUrole(String urole) {
  // this.urole = urole;
  // }

  // public Date getUregdate() {
  // return uregdate;
  // }

  // public void setUregdate(Date uregdate) {
  // this.uregdate = uregdate;
  // }

  // public String getUregdate1() {
  // return uregdate1;
  // }

  // public void setUregdate1(String uregdate1) {
  // this.uregdate1 = uregdate1;
  // }

  // @Override
  // public String toString() {
  // return "MemberDTO [uemail=" + uemail + ", uname=" + uname + ", uphone=" +
  // uphone + ", upw=" + upw + ", uregdate="
  // + uregdate + ", uregdate1=" + uregdate1 + ", urole=" + urole + "]";
  // }

}
