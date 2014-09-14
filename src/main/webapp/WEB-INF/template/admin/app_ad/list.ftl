<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.product.list")} </title>
<meta name="author" content="Sunry" />
<meta name="copyright" content="" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/common.js"></script>
<script type="text/javascript" src="${base}/resources/admin/js/list.js"></script>
<style type="text/css">
.moreTable th {
	width: 80px;
	line-height: 25px;
	padding: 5px 10px 5px 0px;
	text-align: right;
	font-weight: normal;
	color: #333333;
	background-color: #f8fbff;
}

.moreTable td {
	line-height: 25px;
	padding: 5px;
	color: #666666;
}

.promotion {
	color: #cccccc;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $listForm = $("#listForm");
	var $filterSelect = $("#filterSelect");
	var $filterOption = $("#filterOption a");
	
	[@flash_message /]
	
	// 广告筛选
	$filterSelect.mouseover(function() {
		var $this = $(this);
		var offset = $this.offset();
		var $menuWrap = $this.closest("div.menuWrap");
		var $popupMenu = $menuWrap.children("div.popupMenu");
		$popupMenu.css({left: offset.left, top: offset.top + $this.height() + 2}).show();
		$menuWrap.mouseleave(function() {
			$popupMenu.hide();
		});
	});
	
	// 筛选选项
	$filterOption.click(function() {
		var $this = $(this);
		var $dest = $("#" + $this.attr("name"));
		if ($this.hasClass("checked")) {
			$dest.val("");
		} else {
			$dest.val($this.attr("val"));
		}
		$listForm.submit();
		return false;
	});

});
</script>
</head>
<body>
	<div class="path">
		<a href="${base}/admin/common/index.jhtml">${message("admin.path.index")}</a> &raquo; ${message("admin.appAdvertise.list")}
	</div>
	<form id="listForm" action="list.jhtml" method="get">
		<input type="hidden" id="isEnabled" name="isEnabled" value="[#if isEnabled??]${isEnabled?string("true", "false")}[/#if]"/>
		<div class="bar">
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>${message("admin.common.add")}
			</a>
			<div class="buttonWrap">
				<a href="javascript:;" id="refreshButton" class="iconButton">
					<span class="refreshIcon">&nbsp;</span>${message("admin.common.refresh")}
				</a>
				<div class="menuWrap">
					<a href="javascript:;" id="filterSelect" class="button">
						${message("admin.appAdvertise.filter")}<span class="arrow">&nbsp;</span>
					</a>
					<div class="popupMenu">
						<ul id="filterOption" class="check">
							<li>
								<a href="javascript:;" name="isEnabled" val="true"[#if isEnabled?? && isEnabled] class="checked"[/#if]>${message("admin.appAdvertise.enabled")}</a>
							</li>
							<li>
								<a href="javascript:;" name="isEnabled" val="false"[#if isEnabled?? && !isEnabled] class="checked"[/#if]>${message("admin.appAdvertise.notenabled")}</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<table id="listTable" class="list">
			<tr>
				<th>
					<a href="javascript:;" class="sort" name="city">${message("AppAdvertise.city")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="order">${message("AppAdvertise.order")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="imageUrl">${message("AppAdvertise.imageurl")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="linkUrl">${message("AppAdvertise.linkurl")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="isEnabled">${message("AppAdvertise.isEnabled")}</a>
				</th>
				<th>
					<a href="javascript:;" class="sort" name="createDate">${message("admin.common.createDate")}</a>
				</th>
				<th>
					<span>${message("admin.common.handle")}</span>
				</th>
			</tr>
			[#list list as ad]
				<tr>
					<td>
						${ad.city}
					</td>
					<td>
						${ad.order}
					</td>
					<td>
						<a href="${ad.imageUrl}" target="_blank">${ad.imageUrl}</a>
					</td>
					<td>
						<a href="${ad.linkUrl}" target="_blank">${ad.linkUrl}</a>
					</td>
					<td>
						<span class="${ad.isEnabled?string("true", "false")}Icon">&nbsp;</span>
					</td>
					<td>
						<span title="${ad.createDate?string("yyyy-MM-dd HH:mm:ss")}">${ad.createDate}</span>
					</td>
					<td>
						<a href="edit.jhtml?id=${ad.id}">[${message("admin.common.edit")}]</a>
					</td>
				</tr>
			[/#list]
		</table>
	</form>
</body>
</html>