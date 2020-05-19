from TreeLSTM.src import train
from my_code2vec import code2vec
import os

res_dict={}


# os.chdir('my_code2vec/')
os.chdir('my_sdp/my_code2vec/')

res_dict['c2v_pred']=code2vec.predict_file("../my_pred_dir/")

os.chdir('../TreeLSTM/src/')

res_dict['lstm_pred'], res_dict['topk_list']=train.predict_file("../../my_pred_dir/")

print(res_dict)
