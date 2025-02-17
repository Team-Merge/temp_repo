![image](https://github.com/user-attachments/assets/1a4ec03a-8b86-4a75-ae9a-6e7ae66acb04)# 당신을 위한 여행친구 [지구여행]

[![Since](https://img.shields.io/badge/since-2025.01.02-333333.svg)](https://github.com/Team-Merge/jigu_travel)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/Team-Merge/jigu_travel/blob/develop/LICENSE)


[지구 여행]의 백엔드용 git hub 입니다.

<br>

## Demo video
![Debo_video_resize](https://github.com/user-attachments/assets/ff9c3792-7c9b-4257-b2e9-107d7fd6765a)
<br>
고화질 영상은 다운로드후 시청 가능합니다. [다운로드 demo_video](https://github.com/Team-Merge/jigu_travel/raw/develop/demo_video.mp4)

<br>

## 팀 멤버
| <a href="https://github.com/Lucky-SeoYounghyun"><img src="https://github.com/Lucky-SeoYounghyun.png?size=120" width="120"/></a> | <a href="https://github.com/rkdalsrn555"><img src="https://github.com/rkdalsrn555.png?size=120" width="120"/></a> | <a href="https://github.com/dayoonn"><img src="https://github.com/dayoonn.png?size=120" width="120"/></a> | <a href="https://github.com/ekdha235"><img src="https://github.com/ekdha235.png?size=120" width="120"/></a> | <a href="https://github.com/Dah222"><img src="https://github.com/Dah222.png?size=120" width="120"/></a> | <a href="https://github.com/YJH0501"><img src="https://github.com/YJH0501.png?size=120" width="120"/></a> |
|:----------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------:|:---------------------------------------------------------------------:|:-------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------:|
| **서영현** <br/> *(팀장, Full-Stack)* | **강민구** <br/> *(부팀장, BE/Infra)* | **남다윤** <br/> *(Full-Stack)* | **소혜원** <br/> *(Full-Stack)* | **안다희** <br/> *(Full-Stack)* | **양준형** <br/> *(Full-Stack)* |

<br>

## 개발기간
|      내용      | 1월 02일 ~ 1월 5일 | 1월 06일 ~ 1월 12일 | 1월 13일 ~ 1월 19일 | 1월 20일 ~ 1월 26일 | 1월 27일 ~ 2월 2일 | 2월 03일 ~ 2월 09일 | 2월 10일 ~ 2월 16일 |
|:--------------:|:-----------------:|:------------------:|:------------------:|:------------------:|:------------------:|:------------------:|:------------------:|
|    과제 선정    |         O         |          O         |                    |                    |                    |                    |                    |
|   타당성 검토   |                   |          O         |                    |                    |                    |                    |                    |
|   UI/UX디자인   |                   |          O         |          O         |                    |                    |                    |                    |
|    코드개발     |                   |                    |          O         |          O         |          O         |          O         |                    |
|     디버깅      |                   |                    |                    |          O         |          O         |          O         |                    |
| 모듈 통합 테스트 |                   |                    |                    |                    |          O         |          O         |                    |
|     문서화      |         O         |          O         |          O         |          O         |          O         |          O         |          O         |

<br>

## 실제 웹 서비스
내부 사정으로 인하여 서비스 운영이 중지될 수 있습니다.

[지구 여행 홈페이지](https://jigu-travel.kro.kr/home)

<br>

## 개발 선정 과정
- 2024 관광 트렌드에따르면 1인 여행 및 개인화된 여행 수요의 꾸준한 증가세를 보이고 있음

- 소규모 여행시 전문 관광 가이드 섭외는 여전히 어려움

- 이러한 문제를 해결하기 위해 매년 한국 관광공사에서 관광 데이터 활용 공모전을 개최중에 있음

- 이를 위해서 1인 여행 수요 증가와 개인화된 여행의 트렌드에 맞춰, 여행자들에게 더욱 효율적이고 풍부한 여행 경험을 제공할 수 있는 맞춤형 음성 가이드 서비가 필요함

<br>

## 주요 서비스 내용
1. AI 위치기반 실시간 맞춤 장소 추천기능 제공

2. AI 음성 및 채팅 기능 제공

3. AI 객체 탐지 기반 장소 정보 제공

4. 관리자를 위한 이용자관리, 홈페이지 관리 및 요약 정보 제공

5. QnA 게시판을 통하여 각종 문의 기능 제공

<br>

## 적용 기술
### - AI 기술
|      \      |        기술       |      세부 내용     |
|:-----------:|:-----------------:|:------------------|
|     챗봇     |               |               |
|     챗봇     |               |               |
| 카테고리 분류 |    SCV 모델    | - 사용자가 속할 가능성이 높은 여행 카테고리 예측 <br/> - randomforest대비 50% 이상 향상된 정확도 <br/> - 관련 자료: <br/> &nbsp;&nbsp;&nbsp;&nbsp;[1. SVM Dual Formulation](https://medium.com/@sathvikchiramana/svm-dual-formulation-7535caa84f17) <br/> &nbsp;&nbsp;&nbsp;&nbsp;[2. SVM 개념 설명](https://yngie-c.github.io/machine%20learning/2021/03/07/svm/) <br/> &nbsp;&nbsp;&nbsp;&nbsp;[3. 서포트 벡터 머신 - 위키피디아](https://ko.wikipedia.org/wiki/%EC%84%9C%ED%8F%AC%ED%8A%B8_%EB%B2%A1%ED%84%B0_%EB%A8%B8%EC%8B%A0) <br/> &nbsp;&nbsp;&nbsp;&nbsp;[4. SVM을 이용한 디렉토리 기반 기술정보 문서 자동 분류시스템 설계](https://www.dbpia.co.kr/journal/articleDetail?nodeId=NODE07015303) |
| 카테고리 분류 |   COS 유사도   | - 두 벡터의 크기가 아닌 방향에 집중하여 정규화된 데이터에 적합 <br/> - 사용자의 입력 특징을 기존 데이터와 비교하여 가장 유사한 사용자 서치 <br/> - 유사도가 높은 3명에대해 카테고리별로 추천 <br/> - 개인화된 추천을 유사도 기반으로 제공 <br/> - 관련 자료: <br/> &nbsp;&nbsp;&nbsp;&nbsp;[1. 코사인 유사도 기법을 이용한 뉴스 추천 시스템](https://scienceon.kisti.re.kr/srch/selectPORSrchArticle.do?cn=NPAP12013299&utm_source=chatgpt.com) <br/> &nbsp;&nbsp;&nbsp;&nbsp;[2. 코사인 유사도 측정을 통한 행위 기반 인증](https://www.kci.go.kr/kciportal/ci/sereArticleSearch/ciSereArtiView.kci?sereArticleSearchBean.artiId=ART002619181&utm_source=chatgpt.com)  |
|   객체탐지   |               |               |
|   객체탐지   |               |               |

### - 일반 기술
|      \      |        기술       |      세부 내용     |
|:-----------:|:-----------------:|:------------------:|


<br>

## 기대 효과
**사용자 중심의 맞춤형 여행 제공**
- 사용자 선호도와 여행 목적을 학습하여 최적화된 관광경험을 제공
- 해당 여행지에서의 만족감 상승

**AI 음성 가이드로 몰입감 있는 여행**
- 단순히 명소를 소개하는것을 넘어, 사용자 관심사에 맞춘 상세한 역사, 문화적 배경 등 맞춤형 설명 제공

**지역 관광 활성화 및 균형 발전**
- 잘 알려지지 않은 지역 명소를 데이터 기반 추천
- 관광 패턴의 지역적 분산 촉진

**자자체 및 관광 업체 활성화**
- 사용자의 여행 패턴과 선호 데이터를 분석하여 지역 관광 정책 및 전략 수립에 활용
  
<br>

## 코드 사용 방법
아래 두 코드를 다운로드
<br>
[1. FrontEnd](https://github.com/Team-Merge/jigu_travel_fe)
<br>
[2. FastApI](https://github.com/Team-Merge/fastapi_deploy) + [2. FastApI(model)](https://drive.google.com/file/d/1MK9vmjsV11qPoimpKyySVKEu7RjJdtUi/view?usp=drive_link)
<br>

1. frontend 사용 방법


2. fastapi 사용 방법
     1. 가상 환경 생성
     ```
     conda create -y -n [원하는 환경 이름] python=3.12.5
     ```

4. backend 사용 방법


<br>

## 서비스 사용 방법


<br>

## License

```html
Copyright (c) 2025 Team.Merge

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
