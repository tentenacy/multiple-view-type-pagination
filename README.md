# ****multiple-view-type-pagination****

## 문제

- vertical oriented paging에서 multiple view type을 가지는 recycler view의 스크롤 동작이 제대로 되지 않는 현상 발생
- 예상치 못한 현상
    - paging api 무한 호출
    - nested scroll view과 사용한 것처럼 스크롤 값을 제대로 가져오지 못함

## 해결

- PagingData의 insertHeaderItem을 사용하여 Data가 아닌 요소를 Header로 처리