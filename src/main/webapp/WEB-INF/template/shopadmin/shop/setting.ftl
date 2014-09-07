<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("shopadmin.shop.setting")} </title>
<meta name="author" content="Sunry" />
<meta name="copyright" content="" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/input.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $addShopImage = $("#addShopImage");
	var $deleteShopImage = $("a.deleteShopImage");
	var $shopImageTable = $("#shopImageTable");
	var shopImageIndex = ${(shop.shopImages?size)!"0"};
	var $imageBrowserButton = $("#imageBrowserButton");
	var $logoBrowserButton = $("#logoBrowserButton");
	
	[@flash_message /]
	
	$imageBrowserButton.browser();
	$logoBrowserButton.browser();

	// 表单验证
	$inputForm.validate({
		rules: {
			name: "required",
			address: "required",
			description: "required",
			payAccount: "required",
			status: "required",
			image: "required",
			logo: "required"
		}
	});
	
	//增加店铺图片
	$addShopImage.click(function() {
		[@compress single_line = true]
			var trHtml = 
			'<tr>
				<td>
					<input type="file" name="shopImages[' + shopImageIndex + '].file" class="productImageFile" \/>
				<\/td>
				<td>
					<input type="text" name="shopImages[' + shopImageIndex + '].title" class="text" maxlength="200" \/>
				<\/td>
				<td>
					<input type="text" name="shopImages[' + shopImageIndex + '].order" class="text productImageOrder" maxlength="9" style="width: 50px;" \/>
				<\/td>
				<td>
					<a href="javascript:;" class="deleteShopImage">[${message("admin.common.delete")}]<\/a>
				<\/td>
			<\/tr>';
		[/@compress]
		$shopImageTable.append(trHtml);
		shopImageIndex++;
	});
	
	//删除店铺图片
	$deleteShopImage.live("click", function() {
		var $this = $(this);
		$.dialog({
			type: "warn",
			content: "${message("admin.dialog.deleteConfirm")}",
			onOk: function() {
				$this.closest("tr").remove();
			}
		});
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("shopadmin.shop.setting")}
	</div>
	<form id="inputForm" action="update.jhtml" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${shop.id}">
		<table id="shopTable" class="input">
			<tr>
				<th>
					${message("Shop.productCategory")}:
				</th>
				<td>
					<p>${shop.productCategory.name}</p>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.name")}:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text_medium" value="${shop.name}" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					${message("Shop.city")}:
				</th>
				<td>
					<p>${shop.city}</p>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("shopadmin.shop.address")}:
				</th>
				<td>
					<input type="text" id="address" name="address" value="${shop.address}" class="text_medium" maxlength="200" title="${message("shopadmin.shop.addressTitle")}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("shopadmin.shop.description")}:
				</th>
				<td>
					<textarea id="description" name="description" rows="5" cols="60" maxlength="500" class="text">${shop.description}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					${message("shopadmin.shop.notice")}:
				</th>
				<td>
					<textarea id="notice" name="notice" rows="5" cols="60" maxlength="500" class="text">${shop.notice}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("shopadmin.shop.payaccount")}:
				</th>
				<td>
					<input type="text" id="payAccount" name="payAccount" value="${shop.payAccount}" class="text_medium" maxlength="50" title="${message("shopadmin.shop.payaccountTitle")}"/>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.image")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="image" class="text" value="${shop.image}" maxlength="200" title="${message("shopadmin.shop.imageTitle")}" />
						<input type="button" id="imageBrowserButton" class="button" value="${message("admin.browser.select")}" />
						[#if shop.image??]
							<a href="${shop.image}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("Shop.logo")}:
				</th>
				<td>
					<span class="fieldSet">
						<input type="text" name="logo" class="text" value="${shop.logo}" maxlength="200" title="${message("shopadmin.shop.logoTitle")}" />
						<input type="button" id="logoBrowserButton" class="button" value="${message("admin.browser.select")}" />
						[#if shop.logo??]
							<a href="${shop.logo}" target="_blank">${message("admin.common.view")}</a>
						[/#if]
					</span>
				</td>
			</tr>
			<tr>
				<th>
					<span class="requiredField">*</span>${message("shopadmin.shop.status")}:
				</th>
				<td>
					<select id="status" name="status">
						<option value="1" [#if shop.status == 1] selected="selected"[/#if]>${message("shopadmin.shop.status.idle")}</option>
						<option value="2" [#if shop.status == 2] selected="selected"[/#if]>${message("shopadmin.shop.status.busy")}</option>
						<option value="3" [#if shop.status == 3] selected="selected"[/#if]>${message("shopadmin.shop.status.hot")}</option>
					</select>
				</td>
			</tr>
		</table>
		<table id="shopImageTable" class="input tabContent" style="width:60%;margin-left:80px;">
			<tr>
				<td colspan="4">
					<a href="javascript:;" id="addShopImage" class="button">${message("shopadmin.shop.addShopImage")}</a>
				</td>
			</tr>
			<tr class="title">
				<td>
					${message("ShopImage.file")}
				</td>
				<td>
					${message("ShopImage.title")}
				</td>
				<td>
					${message("admin.common.order")}
				</td>
				<td>
					${message("admin.common.delete")}
				</td>
			</tr>
			[#list shop.shopImages as shopImage]
				<tr>
					<td>
						<input type="hidden" name="shopImages[${shopImage_index}].source" value="${shopImage.source}" />
						<input type="hidden" name="shopImages[${shopImage_index}].large" value="${shopImage.large}" />
						<input type="hidden" name="shopImages[${shopImage_index}].medium" value="${shopImage.medium}" />
						<input type="hidden" name="shopImages[${shopImage_index}].thumbnail" value="${shopImage.thumbnail}" />
						<input type="file" name="shopImages[${shopImage_index}].file" class="productImageFile ignore" />
						<a href="${shopImage.large}" target="_blank">${message("admin.common.view")}</a>
					</td>
					<td>
						<input type="text" name="shopImages[${shopImage_index}].title" class="text" maxlength="200" value="${shopImage.title}" />
					</td>
					<td>
						<input type="text" name="shopImages[${shopImage_index}].order" class="text productImageOrder" value="${shopImage.order}" maxlength="9" style="width: 50px;" />
					</td>
					<td>
						<a href="javascript:;" class="deleteShopImage">[${message("admin.common.delete")}]</a>
					</td>
				</tr>
			[/#list]
		</table>
		<table class="input">
			<tr>
				<th>&nbsp;
					
				</th>
				<td>
					<input type="submit" class="button" value="${message("admin.common.submit")}" />
					<input type="button" id="backButton" class="button" value="${message("admin.common.back")}" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>