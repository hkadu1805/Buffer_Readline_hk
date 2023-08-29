var defaultS3 = FileFolderService_1("DefaultS3");

var relativeFilePath = @base_location + "/" +
	@del_sys_name + "/" +
	@SSF_table_name + "/" +
	##get_ssf_payload_xmlfile_name(@exec_date, @base_location, @del_sys_name, @SSF_table_name);
	
var inStream = defaultS3.getStreamBasedFileReadInfo(relativeFilePath).getStream();

var inStreamReader = new("java.io.InputStreamReader",inStream);

var buffer = new("java.io.BufferedReader",inStreamReader);

var payloadRecordCount = 0;

var search_word = @search_word;
var searchWordLength = search_word.length();

<for> var line = buffer.readLine() <comma/> line != null <comma/> line = buffer.readLine()
	<do>
		var lineLength = line.length();
		<for> var i = 0 <comma/> i &lt; (lineLength - wordLength + 1) <comma/> i++
			<do>
				<if> line.substring(i, i + searchWordLength).equals(search_word)
					<then>
						payloadRecordCount++;
					</then>
				</if>
			</do>
		</for>
	</do>
</for>

var matchingIndicator = 'N';

payloadRecordCount = payloadRecordCount + "";

<if> payloadRecordCount.equals(@NR_meta)
	<then>
		matchingIndicator = 'Y';
	</then>
</if>

out.print(@del_sys_name + '|' + @SSF_table_name + '|' + @NR_meta + '|' + payloadRecordCount + '|' + matchingIndicator);

inStream.close();
