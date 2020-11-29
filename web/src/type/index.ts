export interface RankInfo {
    id?: string;
    rankid?: number;
    rankname?: number;
    updatetime?: number;
    appname?: number;
}

export interface MusicInfo {
    musicid?: string;
    musicname?: string;
    remark?: string;
    rankid?: number;
    ranknum?: number;
    updatetime?: string;
    appname?: string;
}

export interface MusicRankInfo {
    id?: string;
    musicid?: string;
    musicname? :string;
    appname?: string;
    hotnum?: number;
    updatetime?:any;
    rankid?: number;
    rankname?: string;
}

export enum APP_TYPE {
    QQ = 'qq',
    KUGOU = 'kugou',
    BAIDU = 'baidu'
}