import React from 'react'
import { useNavigate } from 'react-router-dom';
import './ModalEnroll.css'

const ModalEnroll = (props) => {
    // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
    const { open, close, header, plantName, plantDictId } = props;
    const navigate = useNavigate()

    const goToEnroll = ({plantName,plantDictId }) =>{
        navigate("/enrollment/plant", {state :{plantName:plantName, plantDictId:plantDictId}})
    }

    return (
      // 모달이 열릴때 openModal 클래스가 생성된다.
      <div className={open ? 'openModal modal' : 'modal'}>
        {open ? (
          <section>
            <header className='font-PreL'>
              {header}
              <button className="close" onClick={close}>
                &times;
              </button>
            </header>
            <main>{props.children}</main>
            <footer>
              <button className="font-PreM enrollNo close" onClick={close}>
                취소
              </button>
              <button className="font-PreM enrollYes" onClick={()=>goToEnroll({plantName, plantDictId})}>
                등록
              </button>
            </footer>
          </section>
        ) : null}
      </div>
    );
  };
export default ModalEnroll