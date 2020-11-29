import React, {useEffect, useState} from 'react';
import {Tabs} from 'antd'
import {FireFilled} from '@ant-design/icons'
import {APP_TYPE, MusicInfo, MusicRankInfo, RankInfo} from "../../type";
import Axios from "axios";
import './index.css';

const {TabPane} = Tabs;

interface MusicListProps {
    type: APP_TYPE;
    isAll?: boolean;
}

export default (props: MusicListProps) => {
    const {type, isAll} = props;

    const [rankList, setRankList] = useState<RankInfo[]>([]);
    const [rankId, setRankId] = useState<number>(-1);
    const [musicList, setMusicList] = useState<MusicInfo[]>([])
    const [musicRankList, setMusicRankList] = useState<MusicRankInfo[]>([])


    useEffect(() => {
        if (!isAll) {
            Axios.get(`http://localhost:8082/${type}/renovateRankList`).then((res: any) => {
                console.log(res)
                if (res.status === 200) {
                    setRankList(res.data.data || [])
                    if (res.data && res.data.data && res.data.data[0]) {
                        setRankId(res.data.data[0].rankid)
                    }
                }
            })
        } else {
            Axios.get(`http://localhost:8082/${type}/getAllRank`).then((res: any) => {
                console.log(res)
                if (res.status === 200) {
                    setMusicRankList(res.data.data || [])
                }
            })
        }
    }, []);

    useEffect(() => {
        if (rankId > -1) {
            Axios.get(`http://localhost:8082/${type}/getRankMusic/${rankId}`).then((res: any) => {
                console.log(res)
                if (res.status === 200) {
                    setMusicList(res.data.data || [])
                }
            })
        }
    }, [rankId]);

    const onSelectRank = (activeKey: any) => {
        setRankId(activeKey)
    }

    return (
        <div className='musicList'>
            <div className='listHeader'>{`${type}${isAll ? '热力总榜' : ''}`}</div>
            <div>
                {isAll ? (
                    musicRankList.map((item: MusicRankInfo) => (
                            <div><FireFilled />{item.hotnum}. {item.musicname}</div>
                        ))
                ) : (
                    <Tabs tabPosition='left' size='small' onChange={onSelectRank} style={{height: 400}}>
                        {rankList.map((item: RankInfo) => (
                            <TabPane tab={item.rankname} key={item.rankid}>
                                {musicList.map((item: MusicInfo) => (
                                    <div>{item.ranknum}. {item.musicname}</div>
                                ))}
                            </TabPane>
                        ))}
                    </Tabs>
                )}
            </div>
        </div>
    )
}