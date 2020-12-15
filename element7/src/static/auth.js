function passOrNo(roleId,paths,toPath)
{
  let home='';
  if(toPath=='/'|| toPath=='/login')
  {
    return true;
  }
  if(roleId=='1')
  {
    home='/home';
    if(toPath==home)
    {
      return true;
    }
  }else if(roleId=='2')
  {
    home='/teacherHome';
    if(toPath==home)
    {
      return true;
    }
  }else if(roleId=='3')
  {
    home='/adminHome';
    if(toPath==home)
    {
      return true;
    }
  }
  for(let i=0;i<paths.length;i++)
  {
    let path=home+'/'+paths[i].path;
     if(path.trim()==toPath.trim())
     {
       
       return true;
     }
  }
  
  
  
 return false;
}


export{
  passOrNo
}