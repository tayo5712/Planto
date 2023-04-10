import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './index.css';
// 
import { RecoilRoot } from 'recoil';



// 메인 페이지
import App from './features/home/App';
import Main from './features/home/Main';
import MainPlanto from './features/home/MainPlanto';
import PlantoDetail from './features/home/PlantoDetail';
// 사전 페이지
import Dict from './features/dictionary/DictPage';
import PlantDetail from './features/dictionary/PlantDetail';
// 추천 페이지
import DarkHouse from './features/recommand/DarkHouse';
import LessWater from './features/recommand/LessWater';
import Beginner from './features/recommand/Beginner';
import Expert from './features/recommand/Expert';
// 등록
import Enroll from './features/enrollment/Pot_enroll';
import EnrollPlant from './features/enrollment/Plant_enroll';
// 업적 페이지
import Reward from './features/reward/Reward';
import KakaoLogin from './features/login/KakaoLogin';
import Search from './features/components/SearchBar';

// 접근
import  PrivateRoute from './PrivateRouter'

const token = window.localStorage.getItem('token');

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <RecoilRoot>
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App/>}></Route>
        <Route path="/main" element={<PrivateRoute component={<Main/>} authenticated={token} />}></Route>
        <Route path="/main/planto/:serialnumber" element={<PlantoDetail/>}></Route>
        {/* 사전 */}
        <Route path="/dictionary" element={<Dict/>}></Route>
        <Route path="/reward" element={<PrivateRoute component={<Reward/>}authenticated={token}/>}></Route>
        <Route path="/dictionary/:plantId" element={<PlantDetail/>}></Route>
        {/* 해시태그 페이지 */}
        <Route path="/dictionary/darkhouse" element={<DarkHouse/>}></Route>
        <Route path="/dictionary/lesswater" element={<LessWater/>}></Route>
        <Route path="/dictionary/beginner" element={<Beginner/>}></Route>
        <Route path="/dictionary/expert" element={<Expert/>}></Route>
        <Route path="/kakaoLogin" element={<KakaoLogin/>}></Route>
        {/* 등록 */}
        <Route path="/enrollment" element={<PrivateRoute component={<Enroll/>}authenticated={token}/>}></Route>
        <Route path="/enrollment/plant" element={<PrivateRoute component={<EnrollPlant/>}authenticated={token}/>}></Route>
        {/* 검색 */}
        <Route path="/enrollment/plant/search"element={<PrivateRoute component={<Search/>}authenticated={token}/>}></Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
  </RecoilRoot>
);
