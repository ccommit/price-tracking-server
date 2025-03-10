# price-tracking-server
상품 가격 변동 추적 및 알림 서비스
- PriceTrackerServer는 사용자가 관심 있는 상품의 가격 변동을 추적하고, 변동 내역을 시각적으로 제공하는 서비스입니다. 또한, 사용자가 설정한 조건에 따라 가격이 변동할 때 알림을 받을 수 있습니다.
- 연동 API document : [https://shopping.google.com/?nord=1&pli=1](https://serpapi.com/google-shopping-api#api-parameters-serpapi-parameters-api-key)

# 목적
- **객체지향적 설계**를 통해 유지보수가 용이하게 개발, **클린 코드**를 구현하여 가독성을 높임
- 상품의 가격 추적 및 알림 시스템의 신뢰성 높은 구현
- 사용자 맞춤형 가격 알림 및 통계성 데이터 관리

# 사용기술
- JAVA23, Spring Boot 3.2, JPA, MySQL, Elasticsearch, Redis, Docker, Locust, JUnit5, Spring Batch

# 주요 기능
1. 상품 데이터 및 가격 추적
- Google Shopping API를 활용하여 상품 데이터 수집
- 상품 가격 변동 추적 및 저장
2. 상품 검색 기능
- 검색 기준
  - 상품명
  - 카테고리(1, 2, 3차)
  - 가격 범위
  - 상태 (새상품, 중고상품)
- 정렬 옵션 제공
  - 최신순 (기본)
  - 인기순
  - 가격 낮은순
  - 가격 높은순
  - 할인율 낮은순
  - 할인율 높은순
- 상품 가격 변화 기록 조회
  - 상품별, 기간별 가격 변화 기록을 조회할 수 있는 기능
3. 알림 기능
- 이메일, SMS 알림 지원
- 유저 알림 기준 설정
  - 가격 범위 설정 시 알림
  - 키워드 기준 신규 상품 등록 알림
  - 2차 카테고리별 신규 상품 등록 알림
  - 기간 내 최저가 알림 (유저가 설정한 기간 동안 가장 낮은 가격이 될 때 알림)
4. 카테고리 관리
- 1차 카테고리 (예: 휴대폰)
- 2차 카테고리 (예: 갤럭시, 아이폰)
- 3차 카테고리 (예: 갤럭시25, 아이폰16 등)
- 상품별 할인율 및 가격 통계 (최대 3년까지 제공)
  - 시간별
  - 일별
  - 주별
  - 월별
  - 연별
5. 통계 및 시각화
- 프론트엔드 데모 페이지 제공
- 3차 카테고리 가격 비교
  - 3차 카테고리 내 다양한 상품들의 가격 비교 시각화
- 할인율 추이
  - 한 카테고리의 할인율 평균을 시각화하여 추이 분석
- 상품의 평균 가격 추이
  - 각 상품의 가격 변화 추이를 시간에 따라 시각화
- 상품의 기간별 가격 추이
  - 지정된 기간에 따른 각 상품의 가격 변동 추이 시각화
- 2차 카테고리 상품 수 추이
  - 2차 카테고리 내 상품의 수를 시간에 따라 시각화
- 2차 카테고리의 새 상품/중고 상품 비율
  - 2차 카테고리 내 새 상품과 중고 상품의 비율을 원형 그래프 형태로 시각화
- 상품별 리뷰 점수 (3차 카테고리별 평균 리뷰 점수 제공, 예: 갤럭시21 4.0)
- 트렌드 데이터 제공
  - 카테고리별 및 키워드별 트렌드 조회 (기기별, 성별, 연령대별)
    - 기기별 트렌드
      - PC, 모바일, 전체
    - 성별 트렌드
      - Female, Male, All
    - 연령대별 트렌드
      - All, 10+, 20+, 30+, 40+, 50+, 60+
6. 회원 관리
- 회원가입, 회원 수정, 회원 탈퇴
- 닉네임 중복 체크
- 비밀번호 암호화
- 로그인, 로그아웃
- 사용자 상태 관리
  - 일반회원(active), 비활성 유저(inactive), 이상유저(abnormal), 관리자(admin)
- 이메일 본인 인증
- 비밀번호 변경
- 로그인 이력 조회
  - 기간별 로그인 실패 이유 및 실패 횟수 조회 기능 제공
- 로그인 세션 관리
  - 세션 상태 관리: active, expired, terminated (활성화, 만료, 종료)
  - 세션 상태에 따라 세션 삭제 (만료된 세션은 일정 시간이 지난 후 자동 삭제)
7. 관리자 기능
- 이상 사용자 상태 변경
  - 관리자 권한을 통해 이상 사용자 상태 변경 가능
- 가격 변동 추적 관리 및 모니터링
- 시스템 이상 발생 시 알림 기능
- 로그 및 통계 관리
  
# 확장 계획
- 상품 범위 확대: 전자제품뿐만 아니라 의류, 가전, 도서 등 다양한 카테고리 추가
- 해외 시장 확대: 다국어 지원 및 글로벌 쇼핑몰(아마존, 이베이 등)과 연계하여 글로벌 가격 비교 기능 추가

# ERD
![PriceTracker_ERD](https://github.com/user-attachments/assets/ac394f0c-2a7f-418e-a982-0980fbc36aad)


<details>
  <summary><h1 id="elasticsearch">Elasticsearch</h1></summary>
  <details>
    <summary><h2> productId API 문서</h2></summary>
    <img src="https://github.com/user-attachments/assets/a0b626e7-15a4-4709-b103-1d5684f2aec3" alt="price_tracking_server_elasticsearch_productId" />
      <details open>
        <summary><h2>productId Index mapping 정보</h2></summary>
  
  <pre><code>
  {
    "mappings": {
      "properties": {
        "productId": {
          "type": "keyword"
        },
        "googleProductId": {
          "type": "keyword"
        },
        "productName": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword"
            }
          }
        },
        "url": {
          "type": "keyword"
        },
        "originalPrice": {
          "type": "double"
        },
        "discountRate": {
          "type": "double"
        },
        "discountPrice": {
          "type": "double"
        },
        "shippingFee": {
          "type": "double"
        },
        "currency": {
          "type": "keyword"
        },
        "status": {
          "type": "keyword"
        },
        "createdAt": {
          "type": "date"
        },
        "categories": {
          "type": "nested",
          "properties": {
            "categoryName": {
              "type": "keyword"
            },
            "categoryLevel": {
              "type": "integer"
            }
          }
        }
      }
    }
  }
  </code></pre>
  </details>

  <details open>
    <summary><h3>요청 파라미터</h3></summary>
    <table border="1">
  <thead>
    <tr>
      <th>파라미터</th>
      <th>타입</th>
      <th>필수 여부</th>
      <th>설명</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>productId</td>
      <td>keyword</td>
      <td>✅ 필수</td>
      <td>상품의 고유 ID (검색 기준)</td>
    </tr>
    <tr>
      <td>googleProductId</td>
      <td>keyword</td>
      <td>✅ 필수</td>
      <td>Google 상품 ID (검색 기준)</td>
    </tr>
    <tr>
      <td>productName</td>
      <td>text</td>
      <td>❌ 선택</td>
      <td>상품 이름 (부분 검색 가능)</td>
    </tr>
    <tr>
      <td>url</td>
      <td>keyword</td>
      <td>❌ 선택</td>
      <td>상품 상세 페이지 URL</td>
    </tr>
    <tr>
      <td>originalPrice</td>
      <td>double</td>
      <td>❌ 선택</td>
      <td>원래 가격</td>
    </tr>
    <tr>
      <td>discountRate</td>
      <td>double</td>
      <td>❌ 선택</td>
      <td>할인율 (%) (범위 검색 가능)</td>
    </tr>
    <tr>
      <td>discountPrice</td>
      <td>double</td>
      <td>❌ 선택</td>
      <td>할인 가격 (범위 검색 가능)</td>
    </tr>
    <tr>
      <td>shippingFee</td>
      <td>double</td>
      <td>❌ 선택</td>
      <td>배송비 (범위 검색 가능)</td>
    </tr>
    <tr>
      <td>currency</td>
      <td>keyword</td>
      <td>❌ 선택</td>
      <td>통화 단위 (예: USD, KRW)</td>
    </tr>
    <tr>
      <td>status</td>
      <td>keyword</td>
      <td>❌ 선택</td>
      <td>상품 상태 (예: 새상품, 중고상품)</td>
    </tr>
    <tr>
      <td>createdAt</td>
      <td>date</td>
      <td>❌ 선택</td>
      <td>상품 등록 날짜 (범위 검색 가능)</td>
    </tr>
    <tr>
      <td>categories.categoryName</td>
      <td>keyword</td>
      <td>❌ 선택</td>
      <td>카테고리 이름 (부분 검색 가능)</td>
    </tr>
    <tr>
      <td>categories.categoryLevel</td>
      <td>integer</td>
      <td>❌ 선택</td>
      <td>카테고리 레벨 (1, 2, 3) (범위 검색 가능)</td>
    </tr>
  </tbody>
</table>

  </details>
  <details open><summary><h3>예시 요청</h3></summary>
    <pre><code>
GET /productId/_search
{
  "query": {
    "bool": {
      "must": [
        { "term": { "productId": "P123456" } },
        { "term": { "googleProductId": "13534607668259865762" } },
        { "range": { "createdAt": { "gte": "2025-03-07T00:00:00Z", "lte": "2025-03-07T23:59:59Z" } } },
        { "term": { "status": "새상품" } }
      ]
    }
  }
}
</code></pre>
    
  </details>
  <details open><summary><h3>예시 응답</h3></summary>
    <pre><code>
{
  "hits": {
    "total": { "value": 1, "relation": "eq" },
    "hits": [
      {
        "_index": "productId",
        "_id": "P123456",
        "_source": {
          "productId": "P123456",
          "googleProductId": "13534607668259865762",
          "productName": "갤럭시 25 자급제(네이비, 256GB)",
          "url": "https://www.google.com/shopping/product/1?gl=kr&prds=pid:13534607668259865762",
          "originalPrice": 1126000,
          "discountPrice": 1126000,
          "currency": "KRW",
          "status": "새상품",
          "createdAt": "2025-03-07T06:00:00Z",
          "categories": [
            {
              "categoryName": "휴대폰",
              "categoryLevel": 1
            },
            {
              "categoryName": "갤럭시",
              "categoryLevel": 2
            },
            {
              "categoryName": "갤럭시 25",
              "categoryLevel": 3
            }
          ]
        }
      }
    ]
  }
}
    </code></pre>
    
  </details>
  <details open><summary><h3>응답 파라미터</h3></summary>
    <table>
  <thead>
    <tr>
      <th>파라미터</th>
      <th>타입</th>
      <th>설명</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>productId</td>
      <td>keyword</td>
      <td>상품의 고유 ID</td>
    </tr>
    <tr>
      <td>googleProductId</td>
      <td>keyword</td>
      <td>Google 상품 ID</td>
    </tr>
    <tr>
      <td>productName</td>
      <td>text</td>
      <td>상품 이름 (부분 검색 가능)</td>
    </tr>
    <tr>
      <td>url</td>
      <td>keyword</td>
      <td>상품 상세 페이지 URL</td>
    </tr>
    <tr>
      <td>originalPrice</td>
      <td>double</td>
      <td>원래 가격</td>
    </tr>
    <tr>
      <td>discountRate</td>
      <td>double</td>
      <td>할인율 (%)</td>
    </tr>
    <tr>
      <td>discountPrice</td>
      <td>double</td>
      <td>할인 적용된 가격</td>
    </tr>
    <tr>
      <td>shippingFee</td>
      <td>double</td>
      <td>배송비</td>
    </tr>
    <tr>
      <td>currency</td>
      <td>keyword</td>
      <td>가격의 통화 단위 (예: USD, KRW)</td>
    </tr>
    <tr>
      <td>status</td>
      <td>keyword</td>
      <td>상품 상태 (새상품, 중고상품 등)</td>
    </tr>
    <tr>
      <td>createdAt</td>
      <td>date</td>
      <td>상품 등록 날짜</td>
    </tr>
    <tr>
      <td>categories</td>
      <td>nested</td>
      <td>카테고리 정보 (배열)</td>
    </tr>
    <tr>
      <td>categories.categoryName</td>
      <td>keyword</td>
      <td>카테고리 이름</td>
    </tr>
    <tr>
      <td>categories.categoryLevel</td>
      <td>integer</td>
      <td>카테고리 레벨 (1, 2, 3)</td>
    </tr>
  </tbody>
</table>
  </details>

</details>

  <details><summary><h2>Elacticserach 공통 에러 응답표</h2></summary>
    <table>
  <thead>
    <tr>
      <th>HTTP 상태</th>
      <th>에러 코드</th>
      <th>에러 메시지</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>400</td>
      <td>Bad Request</td>
      <td>잘못된 요청입니다.</td>
    </tr>
    <tr>
      <td>404</td>
      <td>Not Found</td>
      <td>요청된 인덱스를 찾을 수 없습니다.</td>
    </tr>
    <tr>
      <td>500</td>
      <td>Internal Server Error</td>
      <td>서버 내부 오류가 발생했습니다.</td>
    </tr>
    <tr>
      <td>409</td>
      <td>Conflict</td>
      <td>버전 충돌이 발생했습니다.</td>
    </tr>
    <tr>
      <td>403</td>
      <td>Forbidden</td>
      <td>권한이 부족합니다.</td>
    </tr>
  </tbody>
</table>

  </details>
