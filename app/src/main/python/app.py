import requests

def fun(TeacherID, Video ,Image ):
        response = requests.post("https://umairmirza-face-attendance.hf.space/run/predict", json={
            "data": [
                TeacherID,
                Video,
                Image,
            ]
        }).json()

        data = response["data"]
        return data


