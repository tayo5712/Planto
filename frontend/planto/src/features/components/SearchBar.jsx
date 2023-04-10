import React, {useState, useEffect} from 'react'
import {Link} from 'react-router-dom'

import axios from 'axios';
import ModalEnroll from './ModalEnroll';
import './SearchBar.css'
import {RiSearchLine} from "react-icons/ri";
import { HOST } from "../login/OAuth"

function SearchBar() {

//   데이터 받아오기
    const [plants, setPlants] = useState([]);

    useEffect(() => {
        axios({
            method: "get",
            url: `${HOST}/api/v1/dict`,
        }).then(function (response) {
            setPlants(response.data)
            
        });
        
    }, [])
// user Input
const [userInput, setUserInput] = useState('');
const getValue = (e) =>{
    setUserInput(e.target.value.trim())
};

//검색
const searched = plants.filter((item)=> item.name.includes(userInput))




  return (
    <div style={{padding:'1rem'}}>
        <span className='font-PreSB' style={{marginLeft:'9rem', color:'rgb(62, 126, 62)'
        ,fontSize:'1.2rem'}}>내 식물 찾기</span>
        <div style={{display:'flex', marginTop:'1rem'}}>
          <RiSearchLine size="30" style={{marginLeft:'2rem',marginTop:'.45rem', color:'rgb(62, 126, 62)'}}/>
          <input type="text" className='searchinput font-PreM' onChange={getValue} />
        </div>

        {searched.map((item)=>(<Plant plant={item} key={item.plantDictId}/>))}
    </div>
  )

}


function Plant({plant}){
  
    const arr = plant.name.split("(")
    const plantName = arr[0]
    const plantNameSecond = arr[1] !== undefined ? arr[1].slice(0,-1):""
    const [modalOpen, setModalOpen] = useState(false);

    const plantDictId = plant.plantDictId

    const openModal = () => {
      setModalOpen(true);
      console.log(setModalOpen)
    };
    
    const closeModal = () => {
      setModalOpen(false);
    };


    
  
    return (
      <div>
        <ModalEnroll open={modalOpen} close={closeModal} header="이름 확인" plantName={plantName} plantDictId={plantDictId}>
        <div className="modalText">
          <p className="font-PreM">" {plantName} " </p>
          <p className="font-PreL">이 식물을 등록할까요?</p>
        </div>
        </ModalEnroll>
          <div className='plant_card ' onClick={()=>openModal({plantName})}style={{marginBottom:'1.2rem',}}>
            <div className="plant_image"> 
              <div className="img_circle" style={{
              backgroundImage: `url("https://www.nongsaro.go.kr/cms_contents/301/${plant.imagePath}")`
              }}>
              </div>
            </div>
            <div className='plant_text'>
              <p className='font-PreL plant_name_eng'>{plant.plantEng}</p>
              <p className='font-PreM plant_name'>{plantName}</p>  
              <p className='font-PreM text-stone-400 plant_name_second'>{plantNameSecond}</p>
            </div>
          </div>
      </div>
    )
  }
export default SearchBar