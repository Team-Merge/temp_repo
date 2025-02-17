# 당신을 위한 여행친구 [지구여행]

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
| 카테고리 분류 |    SCV 모델    | - 사용자가 속할 가능성이 높은 여행 카테고리 예측 <br/> - randomforest대비 50% 이상 향상된 정확도 <br/> - 관련 논문: <br/> &nbsp;&nbsp;&nbsp;&nbsp;[1. SVM Dual Formulation](https://medium.com/@sathvikchiramana/svm-dual-formulation-7535caa84f17) <br/> [2. SVM 개념 설명](https://yngie-c.github.io/machine%20learning/2021/03/07/svm/) <br/> [3. 서포트 벡터 머신 - 위키피디아](https://ko.wikipedia.org/wiki/%EC%84%9C%ED%8F%AC%ED%8A%B8_%EB%B2%A1%ED%84%B0_%EB%A8%B8%EC%8B%A0) <br/> [4. DBpia 논문](https://www.dbpia.co.kr/journal/articleDetail?nodeId=NODE07015303) |
| 카테고리 분류 |   COS 유사도   |               |
| 카테고리 분류 |     최빈값     |               |
|   객체탐지   |               |               |
|   객체탐지   |               |               |

### - 일반 기술
|      \      |        기술       |      세부 내용     |
|:-----------:|:-----------------:|:------------------:|


<br>

## 기대 효과


<br>

## 코드 사용 방법


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
