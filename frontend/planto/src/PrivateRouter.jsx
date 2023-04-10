import { ReactElement } from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ authenticated, component:Component}) => {
  const token = window.localStorage.getItem("token")
  return(
      authenticated || token?Component:<Navigate to="/" {...alert("로그인이 필요합니다.")}></Navigate>
  )
}

export default PrivateRoute;