
function getCookie()
{
      if(document.cookie.length>0)
      {
       let arrs = document.cookie.split(";");
        for(let i=0;i<arrs.length;i++)
       {
        let arr = arrs[i].split("=");
         if(arr[0].trim() == "roleId".trim())
          {
           return arr[1];
          }
        }
       }
}
export{
	getCookie
}