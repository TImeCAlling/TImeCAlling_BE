# TImeCAlling_BE

## 라이브러리
1. AndroidX
2. Material 3
3. Retrofit 및 관련 라이브러리
4. Glide
5. Gson
6. Hilt
7. OkHttp
8. Tmap
9. Kakao

## 기술 스택
1. Kotlin
2. AndroidX
3.  MVVM
4. SAA
5. Gradle
6. Coroutines

## 아키택쳐
<img width="1017" alt="architecture" src="https://github.com/user-attachments/assets/4fdbbc13-0557-419c-9fab-f235885aa352" />

# Commit Convention

## Branch
- **New Issue**
    - **[Type] where-what**
- **New Branch**
    - **Type/#number-what**
    - Branch는 Issue를 만들고 오른쪽에 있는 Development를 클릭해서 만든다.
- **Commit Message**
    - **[Type/#number] what**
- **Pull Requests**
    - **[Type/#number] where what**
- **Review**
    - 본인이 볼 때 문제가 없다면 LGTM (Looks Good To Me)남기기. 수정이 필요해보이는 부분은 리뷰로 알려주시면 됩니다!
    - 리뷰는 모두가 바로바로 진행해주세요~!! 리뷰 진행 후 Merge를 합니다.
- **Merge**
    - Merge할 때는 중요한 값들이 올라갔는지 확인하기⛔ API KEY 유출하면 절대절대절대절대 안됩니다.
 
### Type

- **[FEAT]** : 새로운 기능에 대한 커밋
- **[FIX]** : 버그 수정에 대한 커밋
- **[BUILD]** : 빌드 관련 파일 수정 / 모듈 설치 또는 삭제에 대한 커밋
- **[CHORE]** : 그 외 자잘한 수정에 대한 커밋
- **[DEL]** : 쓸모없는 코드나 파일 삭제
- **[UI]** : UI 작업
- **[FIX]** : 버그 및 오류 해결
- **[HOTFIX]** : issue나 QA에서 문의된 급한 버그 및 오류 해결
- **[MERGE]** : 다른 브랜치와의 MERGE
- **[MOVE]** : 프로젝트 내 파일이나 코드의 이동
- **[RENAME]** : 파일 이름 변경
- **[REFACTOR]** : 전면 수정
- **[DOCS]** : README나 WIKI 등의 문서 개정

# Code Convention

### XML Naming

- **XML file**
    - activity_main
    - item_main
    - menu_main
- **drawable**
    - Button 아이콘 ⇒ ic_[where]_[what]
        - 공용으로 사용될 경우는 where을 생략 ex)ic_back
    - 이미지 ⇒ img_[where]_[what]
    - 배경 ⇒ bg_[where]_[what]
    - 모양 ⇒ shape_[shape]_[radius]_[color]_[etc]
        - ex) shape_rect_12_white_fill
        - ex) shape_rect_top30_white_fill_gray_line
- **selector**
    - sel_[where]_[type]_[what].xml
        - ex) sel_main_icon_bnv_profile

---

### Component Naming

[view]_[where]_[description] : 뷰이름_화면_무엇을 나타내는지

ex) tv_main_title

- TextView ⇒ **tv**
- EditText ⇒ **et**
- RecyclerView ⇒ **rv**
- ImageView ⇒ **iv**
- Button, ImageButton ⇒ **btn**
- xxLayout ⇒ **layout**
- ViewPager ⇒ **vp**
- TabLayout ⇒ **tab**
- Chip ⇒ **chip**
- Toolbar ⇒ **toolbar**
- ScrollView ⇒ **sv**
- BottomNavigation ⇒ **bnv**
- FragmentContainerView ⇒ **fcv**
- View(밑줄, 경계선, 라인) ⇒ **view**
- FloatingActionButton ⇒ **fab**
- CardView ⇒ **cv**

---

### Kotlin Naming

- **Class & Interface**
    - ex) LoginActivity, HomeFragment
- **함수 & 변수**
    - initXXX() : 초기화 함수 이름
        - init[View]ClickListener : 클릭 리스너 설정
        - init[NameView]Adapter : 리사이클러뷰 어댑터 설정
        
        ```kotlin
        fun initPresentAdapter() {
        	binding.nameRv.adapter = PresentAdapter()
        }
        ```
        
    - updateXXX() : 갱신 함수 이름
    - removeXXX() : 삭제 함수 이름
    - setupXXX() : ViewModel을 observe()할 때 모아놓는 함수 이름
        - setup[ValueName]()
    - getXXX() : Return이 있는 데이터를 불러올 때 함수 이름
    - findXXX() : 특정 객체를 찾는 함수 이름
    - 복수형을 가져올 때는 뒤에 s를 붙인다 : getBrands() 꼴
    - Raw 값으로부터 enum을 찾을 때 함수 이름은 find() 로 한다.
- 서버 통신 함수
    - getXXX() → getUserList()
    - deleteXXX() → deleteUser()
    - putXXX() → putProfile()
    - postXXX() → postMusic()
