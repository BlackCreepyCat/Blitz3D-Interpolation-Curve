Global gfxwidth=400
Global gfxheight=300

Global freq=10
Global amp=200


Global sType=1


SeedRnd MilliSecs()

Graphics gfxwidth,gfxheight,16,2
AppTitle "1D Interpolation - Keys 1-5 Change interpolation"


Dim point(freq,2)


For t=1 To freq
	point(t,1)=((gfxwidth/2)-150)+(300/freq)*t
	point(t,2)=(gfxheight/2+(amp/2))-Rand(0,amp)
Next


SetBuffer BackBuffer()

While Not KeyHit(1)
	Cls
	
	
	If KeyHit(2) sType=1
		If KeyHit(3) sType=2
			If KeyHit(4) sType=3
				If KeyHit(5) sType=4
					If KeyHit(6) sType=5
						
						DrawGraph()
						
						Color 255,255,255
						Text 0,0,"Freqency: "+freq
						Text 0,12,"Amplitude: "+amp
						
						Flip
					Wend
					End
					
					
Function DrawGraph()
	oldlx=point(1,1)
	oldly=point(1,2)
	For t=1 To freq-1
		
		For v=1 To 10
			
			If t-1<1
				oldy=point(t,2)
			Else	
				oldy=point(t-1,2)
			EndIf
			
			
			ay=point(t,2)
			by=point(t+1,2)
			
			
			If t+2>freq
				newy=point(t+1,2)
			Else
				newy=point(t+2,2)
			EndIf
			
			
			lx#=point(t,1)+((point(t+1,1)-point(t,1))/10.0)*v
			
			Select sType
					
				Case 1
					ly#=CosineInterpolate(ay,by,v/10.0)
					Color 255,255,255 Text 0,24,"Cosine Interpolation"
					
				Case 2
					ly#=LinearInterpolate(ay,by,v/10.0)
					Color 255,255,255 Text 0,24,"Linear Interpolation"
					
				Case 3
					ly#=CubicInterpolate(oldy,ay,by,newy,v/10.0)
					Color 255,255,255 Text 0,24,"Cubic Interpolation"
					
				Case 4
					ly#=HermiteInterpolate(oldy,ay,by,newy,v/10.0)
					Color 255,255,255 Text 0,24,"Hermite Interpolation"
					
				Case 5
					ly#=HermiteInterpolate(0.5*(by-oldy),ay,by,0.5*(newy-ay),v/10.0)
					Color 255,255,255 Text 0,24,"Hermite Interpolation (Catmull-Rom splines)"
					
			End Select
			
			Color 255,0,0
			Line oldlx,oldly,lx,ly
			
			oldlx=lx
			oldly=ly
			
		Next
		
		Color 0,255,0
		Rect point(t,1)-1,point(t,2)-1,3,3
		
	Next
	Rect point(freq,1)-1,point(freq,2)-1,3,3
	
End Function


Function CubicInterpolate#(v0#,v1#,v2#,v3#,x#)
	p#=(v3-v2)-(v0-v1)
	q#=(v0-v1)-p
	r#=v2-v0
	s#=v1
	Return (P*(x^3))+(Q*(x^2))+(R*x)+S
End Function


Function HermiteInterpolate#(v0#,v1#,v2#,v3#,x#)
	p#=2*(x^3)-3*(x^2) + 1
	q#=-2*(x^3)+3*(x^2)
	r#=x^3-2*(x^2)+x
	s#=x^3-x^2
	Return p*v1+q*v2+r*v0+s*v3
End Function


Function CosineInterpolate#(a#,b#,x#)
	ft#=x#*180
	f#=(1-Cos(ft#))*0.5
	Return a#*(1.0-f#)+b#*f#
End Function


Function LinearInterpolate#(a#,b#,x#)
	Return a#+(b#-a#)*x#
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D