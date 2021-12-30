<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>



<div class="tm-header" id="tm-header">
	<div class="tm-header-wrapper">
		<button class="navbar-toggler" type="button"
			aria-label="Toggle navigation">
			<i class="fas fa-bars"></i>
		</button>
		<div class="tm-site-header">
			<div class="mb-3 mx-auto">
				<a href="main.pdo"> <img alt="4TeamLogo" src="img/logo2.PNG"
					class="mlogo">
				</a>
			</div>

		</div>
		<nav class="tm-nav" id="tm-nav">
			<ul>
			<li class="tm-nav-myitem myActive" id="main"><a href="main.pdo"
					class="tm-nav-link"> <i class="fas fa-home"></i> Blog Home
				</a> <a href="post.pdo?category=hot" class="tm-nav-option">&nbsp;&nbsp;
						<i class="fas fa-thumbs-up"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;인기글
				</a> <a href="post.pdo?category=chi" class="tm-nav-option">&nbsp;&nbsp;
						<i class="fas fa-drumstick-bite"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;치킨
				</a> <a href="post.pdo?category=piz" class="tm-nav-option">&nbsp;&nbsp;
						<i class="fas fa-pizza-slice"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;피자
				</a> <a href="post.pdo?category=ham" class="tm-nav-option">&nbsp;&nbsp;
						<i class="fas fa-hamburger"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;햄버거
				</a> <a href="post.pdo?category=kor" class="tm-nav-option">&nbsp;&nbsp;
						<i class="fas fa-bread-slice"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한식
				</a> <a href="post.pdo?category=cha" class="tm-nav-option">&nbsp;&nbsp;
						<i class="fas fa-bacon"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;중식
				</a> <a href="post.pdo?category=jap" class="tm-nav-option">&nbsp;&nbsp;
						<i class="fas fa-fish"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일식
				</a></li>
				
				<li class="tm-nav-item" id="posting"><a href="InsertPost.jsp"
					class="tm-nav-link"> <i class="fas fa-pen"></i> Posting
				</a></li>

				<li class="tm-nav-item"><a href="#"
					onclick="checkAlert('logOut.ucdo?pnum=${param.pnum}','로그아웃하시겠어요???')"
					class="tm-nav-link"> <i class="fas fa-users"></i> Logout

				</a></li>
				<li class="tm-nav-item" id="myPage"><a href="mypage.ucdo" 
					class="tm-nav-link"> <i class="fas fa-file"></i> MyPage
				</a></li>
				
				<li class="tm-nav-item" id="contactUs"><a href="About.jsp" class="tm-nav-link">
                        <i class="far fa-comments"></i>
                        Contact Us
                    </a></li>
			</ul>
		</nav>
		   	<!-- <div style="width: 54px; display: inline-block;"id=BJBlog><a rel="nofollow" href="https://fb.com/templatemo" ><img alt="이병재 블로그" src="img/BJ2.png"></a></div>
			<div style="width: 54px; display: inline-block;"id="HTBlog"><a href="https://twitter.com" ><img alt="오현택 블로그" src="img/HT2.png"></a></div>
			<div style="width: 54px; display: inline-block;"id="HJBlog"><a href="https://instagram.com" ><img alt="김혁재 블로그" src="img/HJ2.png"></a></div>
			<div style="width: 54px; display: inline-block;"id="YNBlog"><a href="https://linkedin.com" ><img alt="이예나 블로그" src="img/YN2.png"></a></div>
			<div style="width: 54px; display: inline-block;"id="THBlog"><a href="https://linkedin.com" ><img alt="이태호 블로그" src="img/TH2.png"></a></div> -->
			<ul id="blogLinks">
				<li class="blog" id="BJBlog"><a rel="nofollow" href="https://github.com/PracLee" target="_blank"><img alt="이병재 깃허브" src="img/BJ2.png"></a></li>
				<li class="blog" id="HTBlog"><a href="https://h-coding.tistory.com" target="_blank"><img alt="오현택 블로그" src="img/HT2.png"></a></li>
				<li class="blog" id="HJBlog"><a href="https://github.com/James940909" target="_blank"><img alt="김혁재 깃허브" src="img/HJ2.png"></a></li>
				<li class="blog" id="YNBlog"><a href="https://blog.naver.com/rn8958" target="_blank"><img alt="이예나 블로그" src="img/YN2.png"></a></li>
				<li class="blog" id="THBlog"><a href="https://blog.naver.com/leeth0401" target="_blank"><img alt="이태호 블로그" src="img/TH2.png"></a></li>
			</ul>

	</div>
</div>
