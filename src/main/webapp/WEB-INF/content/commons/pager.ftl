	        	
					        	
				<input type="hidden" id="pageNumber" name="pageNo" value="${page.pageNo}" />
		        <!-- start of 分页 -->
				<#if (totalPages > 1)>
					<div class=" page right">
						<select id="pageSize" class="c_select" name="pageSize">
							<option value="10" <#if page.pageSize=10>selected</#if>>10</option>
							<option value="20" <#if page.pageSize=20>selected</#if>>20</option>
							<option value="50" <#if page.pageSize=50>selected</#if>>50</option>
							<option value="100" <#if page.pageSize=100>selected</#if>>100</option>
						</select>
						<#if isFirst>
							<a class="firstPage">首页</a>
						<#else>
							<a class="firstPage" href="javascript: $.pageSkip(${firstPageNumber});">首页</a>
						</#if>
						<#if hasPrevious>
							<a class="previousPage" href="javascript: $.pageSkip(${previousPageNumber});">上一页</a>
						<#else>
							<a class="previousPage">上一页</a>
						</#if>
						<#list segment as segmentPageNumber>
							<#if (segmentPageNumber_index == 0 && segmentPageNumber > firstPageNumber + 1)>
								<span class="pageBreak">...</span>
							</#if>
							<#if segmentPageNumber != pageNumber>
								<a href="javascript: $.pageSkip(${segmentPageNumber});">${segmentPageNumber}</a>
							<#else>
								<a class="currentPage current">${segmentPageNumber}</a>
							</#if>
							<#if (!segmentPageNumber_has_next && segmentPageNumber < lastPageNumber - 1)>
								<span class="pageBreak">...</span>
							</#if>
						</#list>
						<#if hasNext>
							<a class="nextPage" href="javascript: $.pageSkip(${nextPageNumber});">下一页</a>
						<#else>
							<a class="nextPage">下一页</a>
						</#if>
						<#if isLast>
							<a class="lastPage">末页</a>
						<#else>
							<a class="lastPage" href="javascript: $.pageSkip(${lastPageNumber});">末页</a>
						</#if>
						<!--
						<span class="pageSkip">
							<span>共${totalPages}页</span>
							 <span class="page_info">
							 	到第<input id="pageNumber" name="pageNumber" value="${pageNumber}" class="c_input_text" onpaste="return false;" />页 
							 	<input class="btn_submit" name="" type="submit" value="确 定">
							 </span>
						</span>
						-->
					</div>
				</#if>
	        	<!-- end of 分页 -->
