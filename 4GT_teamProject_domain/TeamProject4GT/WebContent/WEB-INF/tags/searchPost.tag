<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>

		<div class="row tm-row">
			<div class="col-12">
				<form action="findpost.pdo" method="post" class="form-inline tm-mb-80 tm-search-form">
					<select name="condition" id="schSelect" class="form-control tm-search-input">
            			<option selected value="title">제목</option>
            			<option value="writer">작성자</option>         				
            			<option value="content">내용</option>         				
     				</select>
					<input class="form-control tm-search-input" name="findWord"
						type="text" placeholder="Search..." aria-label="Search">
					<button class="tm-search-button" type="submit">
						<i class="fas fa-search tm-search-icon" aria-hidden="true"></i>
					</button>
				</form>
			</div>
		</div>