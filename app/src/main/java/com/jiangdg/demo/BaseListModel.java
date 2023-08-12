package com.jiangdg.demo;

import android.annotation.SuppressLint;

import lombok.Data;


@SuppressLint("ParcelCreator")
@Data
public class BaseListModel extends BaseModel {

  // 현재 페이지 번호
  private int page_num = 0;
  // 리스트 갯수
  private int list_cnt = 0;
  // 총 페이지 수
  private int total_page = 1;
  // 총 아이템 갯수
  private int total_cnt = 0;

  private int current_page = 0;

  /**
   * 다음 페이지 얻어오기
   *
   * @return
   */
  public int getNextPage() {
    if (current_page < 1) {
      return 1;
    } else {
      return current_page + 1;
    }
  }

  /**
   * 페이지 초기화
   */
  public void resetPage() {
    this.current_page = 0;
  }

  /**
   * 불러올 페이지가 더 있는지 확인
   *
   * @return
   */
  public boolean isMore() {
    if (total_page > current_page) {
      return true;
    } else {
      return false;
    }
  }

}
