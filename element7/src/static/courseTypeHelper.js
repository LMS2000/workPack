 function updateData(data){
        for(let i=0;i<data.length;i++)
        {
          if(data[i].courseType=='0')
          {
            data[i].courseType='选修课';
          }
          else if(data[i].courseType=='1'){
            data[i].courseType='公共限定课';
          }
          else if(data[i].courseType=='2')
          {
            data[i].courseType='必修课';
          }else if(data[i].courseType=='3')
          {
            data[i].courseType='校际课';
          }
        }
				return data;
      }
	function parseData(data){
		if(data=='选修课')
		{
			return 0;
		}else if(data=='公共限定课')
		{
			return 1;
		}else if(data=='必修课')
		{
			return 2;
		}else if(data=='校际课')
		{
			return 3;
		}
		
	}		
			export{
				updateData,parseData
			}