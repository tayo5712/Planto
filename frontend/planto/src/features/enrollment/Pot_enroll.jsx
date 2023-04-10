import './pot_enroll.css';
import React, { useState } from 'react';
import { QrReader } from 'react-qr-reader';
import BottomNav from '../../features/nav/BottomNav';
import { BsArrowRightCircleFill } from "react-icons/bs";
import QrReaderImg from "../../assets/background/qr_scan_img.png"
import axios from 'axios'
import { HOST } from '../login/OAuth'
import { useNavigate } from 'react-router-dom';
import back_green from "../../assets/icons/back_green.png"


function Pot_enroll() {
  const navigate = useNavigate();

  const [qrLoading, setQrLoading] = useState(false)
  const [serialNo, setSerialNo] = useState()

  const previewStyle = {
    height:825,
    width: 1100
  };

  function setPlant(){
    const backHost = `${HOST}/api/v1/pot`
    const token = window.localStorage.getItem('token');
    console.log(serialNo)
    axios({
      url: backHost,
      method: 'POST',
      data: {
        potId : serialNo
      },
      headers: {
        Authorization: token
      }
      }).then((res) => {
        window.localStorage.setItem("potSerial", serialNo)
        navigate('/enrollment/plant', { state :{ serialNo : serialNo}})
        console.log(res)
    }).catch(e =>{
      console.log(e)
    })
  }


  return (
    <div id='Pot-enroll'>
      <img src={back_green} onClick={() =>{navigate(-1)}} alt="back_btn" className='circle-btn' />

      <div>
        <img className='qr-reader-frame' src={QrReaderImg} alt="qr reader bg" />
      </div>

      {
        qrLoading &&
        <div className='info-frame'>
          <div className='info-wrapper'>
            <div className='info'>
              <span>
                <strong className='hTag font-PreM'>나의 플랜토 정보 입력</strong>
                <br />
                <span className='pTag font-PreR'>추가 정보를 입력해 플랜토를 시작합니다!</span>
              </span>
            </div>
            <BsArrowRightCircleFill onClick={() => setPlant()} className='circle-right-btn'/>
          </div>
        </div>
      }

      <div id='qr-reader'>
        <QrReader
          constraints={ {facingMode: 'environment'}}
          onResult={(result, error) => {
            if (!!result) {
              setSerialNo(result?.text)
              setQrLoading(true)
            }

            // if (!!error) {
            //   console.info(error);
            // }
          }}
          // videoStyle={previewStyle}
          videoContainerStyle={previewStyle}
          videoStyle={{ width: '100%' }}
        />
      </div>
      <BottomNav />
      
    </div>
  );
}

export default Pot_enroll