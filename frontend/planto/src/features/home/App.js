import './App.css';
import Login from '../login/Login';
// 이미지
import Back from '../../assets/background/appback.jpg'
import Logo from '../../assets/icons/logo.png'
import phone from "../../assets/web/phone.png"
import bla1 from "../../assets/web/bla1.png"
import bla2 from "../../assets/web/bla2.png"
import qr from "../../assets/web/webqr.png"
import proto from "../../assets/web/proto.png"

function App() {

  return ( 
  <div>

    {/* 모바일 */}
    <div className="mobile">
      <div className='App'>
        <div className="appback"style={{width:'100vw',height:'100vh',
        paddingTop:'10rem', backgroundImage:`url("${Back}")`,backgroundSize:'cover',textAlign: ' center'
        }}>      
          <div>
            <img src={Logo} alt="logo"
            style={{
              width:'3rem',
              margin:'auto',
              marginBottom:'1rem',
            }}></img>
            <div className='font-PreM apptext ' style={{marginBottom:'1.5rem'}}>
              <p style={{fontSize:'1.5rem',fontFamily: 'RIDIBatang'}}> 마음을 나누는 </p>
              <p style={{fontSize:'1.5rem',fontFamily: 'RIDIBatang'}}> 나만의 반려식물, 플랜토 </p>
              
            </div>
            <Login/>
          </div>
        </div>
      </div>
    </div>
    {/* 웹 */}
      <div className='web'>
      <p className="webtitle font-PreB">PLANTO</p>
        <div className="web2">
          <div style={{display:'flex', alignItems:'center',justifyContent:'center'}}>
              <div style={{margin:'3vw'}}>
                <p className="font-PreL "> 당신과 소통하며 마음을 나누는 </p>
                <p className="font-PreL "> 반려식물 플랜토 서비스는 </p>
                <p className="font-PreB " style={{color:'#329E5D'}}> 모바일에 최적화 되어있습니다 </p>
                <div className="webqr"style={{display:'flex'}}>
                  <img src={qr} style={{width:'8vw'}}></img>
                  <div>
                  <p className="font-PreM websubtext"> 편리하고 안정적인 </p>
                  <p className="font-PreM websubtext"> 모바일 서비스로 이동</p>
                  </div>
                </div>
              </div>
              <div style={{display:'flex'}}>
                <img src={phone} style={{width:'13vw'}}></img>
                <div>
                <img src={bla1} style={{width:'13vw', marginLeft:'2vw'}}></img>
                <img src={bla2} style={{width:'13vw', marginLeft:'2vw'}}></img>
                <img src={proto} style={{width:'8vw', marginLeft:'15vw'}}></img>
                </div>
              </div>  
          </div>
        </div>      
      </div>
  </div>

  );
  
}

export default App;
