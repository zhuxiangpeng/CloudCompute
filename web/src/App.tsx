import React from 'react';
import './App.css';
import MusicList from './components/musicList';
import {APP_TYPE} from "./type";
import {Row, Col} from "antd";

function App() {


  return (
    <div className="App">
      <div>音乐热榜</div>
      <Row>
        <Col span={8}>
          <MusicList type={APP_TYPE.QQ} />

        </Col>
        <Col span={8}>
          <MusicList type={APP_TYPE.KUGOU} />

        </Col>
        <Col span={8}>
          <MusicList type={APP_TYPE.BAIDU} />

        </Col>
      </Row>
      <Row>
        <Col span={8}>
          <MusicList type={APP_TYPE.QQ} isAll={true} />

        </Col>
        <Col span={8}>
          <MusicList type={APP_TYPE.KUGOU} isAll={true} />

        </Col>
        <Col span={8}>
          <MusicList type={APP_TYPE.BAIDU} isAll={true} />

        </Col>
      </Row>
    </div>
  );
}

export default App;
