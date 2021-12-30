<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
 <script type="text/javascript" src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#filename").on('change', function(){
                readURL(this);
            });
        });
        function readURL(input) {
            if (input.files && input.files[0]) {
               var reader = new FileReader();
               reader.onload = function (e) {
                  $('#preImage').attr('src', e.target.result);
               }
               reader.readAsDataURL(input.files[0]);
            }
        }
</script>

<div class="wrapper">

		<div class="imagebox">
			<img alt="${userInfoData.id}_profile" id="preImage" src="${userInfoData.profile}">
		</div>
		<form method="post" enctype="multipart/form-data"
			action="userProfileEdit.ucdo">
			<div class="fileUpLoad">
				<p>userInfo : ${userInfoData}</p>
				<input type="file" name="filename1" id="filename" onchange="readImage(event)"> 
				<input type="submit" value="업로드!">
			</div>
		</form>
	</div>