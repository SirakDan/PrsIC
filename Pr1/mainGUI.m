function varargout = mainGUI(varargin)
% MAINGUI MATLAB code for mainGUI.fig
%      MAINGUI, by itself, creates a new MAINGUI or raises the existing
%      singleton*.
%
%      H = MAINGUI returns the handle to a new MAINGUI or the handle to
%      the existing singleton*.
%
%      MAINGUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in MAINGUI.M with the given input arguments.
%
%      MAINGUI('Property','Value',...) creates a new MAINGUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before mainGUI_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to mainGUI_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help mainGUI

% Last Modified by GUIDE v2.5 08-Nov-2013 12:49:04

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @mainGUI_OpeningFcn, ...
                   'gui_OutputFcn',  @mainGUI_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before mainGUI is made visible.
function mainGUI_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to mainGUI (see VARARGIN)

%DEFINE THE 2-D MAP ARRAY
global MAX_X;
global MAX_Y;
global MAX_VAL;
global MAP;
global cost;
global img;
global HEIGHT;
HEIGHT = 5;
MAX_X=10;
MAX_Y=10;
%Objects in each coordinate
MAP=2*(ones(MAX_X,MAX_Y));
MAX_VAL = 10;
img = zeros(MAX_X,MAX_Y);
% Obtain Obstacle, Target and Robot Position
% Initialize the MAP with input values
% Obstacle=-1,Target = 0,Robot=1,Space=2
j=0;
x_val = 1;
y_val = 1;
axis([1 MAX_X+1 1 MAX_Y+1])
grid on;
hold on;
n=0;%Number of Obstacles

% Choose default command line output for mainGUI
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes mainGUI wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = mainGUI_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;



function x_size_Callback(hObject, eventdata, handles)
% hObject    handle to x_size (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of x_size as text
%        str2double(get(hObject,'String')) returns contents of x_size as a double


% --- Executes during object creation, after setting all properties.
function x_size_CreateFcn(hObject, eventdata, handles)
% hObject    handle to x_size (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function y_size_Callback(hObject, eventdata, handles)
% hObject    handle to y_size (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of y_size as text
%        str2double(get(hObject,'String')) returns contents of y_size as a double


% --- Executes during object creation, after setting all properties.
function y_size_CreateFcn(hObject, eventdata, handles)
% hObject    handle to y_size (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in size_button.
function size_button_Callback(hObject, eventdata, handles)
global MAX_X;
global MAX_Y;

MAX_X = str2double(get(handles.x_size, 'String'));
MAX_Y = str2double(get(handles.y_size, 'String'));
axis([1 MAX_X+1 1 MAX_Y+1]);
grid on;
hold on;


% --- Executes on button press in setTarget_button.
function setTarget_button_Callback(hObject, eventdata, handles)
% hObject    handle to setTarget_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

h=msgbox('Please Select the Target using the Left Mouse button');
uiwait(h,5);
if ishandle(h) == 1
    delete(h);
end
xlabel('Please Select the Target using the Left Mouse button','Color','black');
but=0;
while (but ~= 1) %Repeat until the Left button is not clicked
    [xval,yval,but]=ginput(1);
end
xval=floor(xval);
yval=floor(yval);
global xTarget;
global yTarget;
xTarget=xval;%X Coordinate of the Target
yTarget=yval;%Y Coordinate of the Target
global MAP;
MAP(xval,yval)=0;%Initialize MAP with location of the target
plot(xval+.5,yval+.5,'gd');
text(xval+1,yval+.5,'Target')

% --- Executes on button press in setWalker_button.
function setWalker_button_Callback(hObject, eventdata, handles)
% hObject    handle to setWalker_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
h=msgbox('Please Select the Vehicle initial position using the Left Mouse button');
uiwait(h,5);
if ishandle(h) == 1
    delete(h);
end
xlabel('Please Select the Vehicle initial position ','Color','black');
but=0;
while (but ~= 1) %Repeat until the Left button is not clicked
    [xval,yval,but]=ginput(1);
    xval=floor(xval);
    yval=floor(yval);
end
global xStart;
global yStart;

xStart=xval;%Starting Position
yStart=yval;%Starting Position
global MAP;
MAP(xval,yval)=1;
 plot(xval+.5,yval+.5,'bo');
%End of obstacle-Target pickup

% --- Executes on button press in setObstables_button.
function setObstables_button_Callback(hObject, eventdata, handles)
% hObject    handle to setObstables_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
h=msgbox('Select Obstacles using the Left Mouse button,to select the last obstacle use the Right button');
  xlabel('Select Obstacles using the Left Mouse button,to select the last obstacle use the Right button','Color','blue');
uiwait(h,5);
if ishandle(h) == 1
    delete(h);
end
global MAP;
but=1;
while but == 1
    [xval,yval,but] = ginput(1);
    xval=floor(xval);
    yval=floor(yval);
    MAP(xval,yval)=-1;%Put on the closed list as well
    plot(xval+.5,yval+.5,'ro');
 end%End of While loop
 


% --- Executes on button press in start_button.
function start_button_Callback(hObject, eventdata, handles)
global MAX_X;
global MAX_Y;
global MAP;
global xTarget;
global yTarget;
global xWaypoint;
global yWaypoint;
% hObject    handle to start_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%LISTS USED FOR ALGORITHM
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%OPEN LIST STRUCTURE
%--------------------------------------------------------------------------
%IS ON LIST 1/0 |X val |Y val |Parent X val |Parent Y val |h(n) |g(n)|f(n)|
%--------------------------------------------------------------------------

global cost;
global xStart;
global yStart;
global HEIGHT;
global img;
xNode=xStart;
yNode=yStart;
p=plot(xNode+.5,yNode+.5,'bo');

[m,n] = size(xWaypoint)
xWaypoint(1, n+1)=xTarget;
yWaypoint(1, n+1)=yTarget;

%Inicio del bucle. 
%Idea: que los waypoints sean metas, ir cambiando puntos de inicio y meta.
for posicion=1:1:n+1,
    if(posicion ~= 1) 
        xStart=xTarget;
        yStart=yTarget;
    end
    xTarget=xWaypoint(posicion);
    yTarget=yWaypoint(posicion);
    ventana=msgbox('Calculating... If the map is big, this may take a while!')
    edithandle = findobj(ventana,'Style','pushbutton')
    set(edithandle,'Visible','off')
    OPEN=[];
%CLOSED LIST STRUCTURE
%--------------
%X val | Y val |
%--------------
% CLOSED=zeros(MAX_VAL,2);
    CLOSED=[];

%Put all obstacles on the Closed list
    k=1;%Dummy counter
    i=0;
    j=0;
    for i=1:1:MAX_X
        for j=1:1:MAX_Y
            if(MAP(i,j) == -1)
                CLOSED(k,1)=i; 
                CLOSED(k,2)=j; 
                k=k+1;
            end
        end
    end
    CLOSED_COUNT=size(CLOSED,1);

%set the starting node as the first node


    OPEN_COUNT=1;
    path_cost=0;
    goal_distance=distance(xNode,yNode,xTarget,yTarget);
    OPEN(OPEN_COUNT,:)=insert_open(xNode,yNode,xNode,yNode,path_cost,goal_distance,goal_distance);
    OPEN(OPEN_COUNT,1)=0;
    CLOSED_COUNT=CLOSED_COUNT+1;
    CLOSED(CLOSED_COUNT,1)=xNode;
    CLOSED(CLOSED_COUNT,2)=yNode;
    NoPath=1;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% START ALGORITHM
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    while((xNode ~= xTarget || yNode ~= yTarget) && NoPath == 1)
%  plot(xNode+.5,yNode+.5,'go');
    exp_array=expand_array(xNode,yNode,path_cost,xTarget,yTarget,CLOSED,MAX_X,MAX_Y, img, HEIGHT);
    exp_count=size(exp_array,1);
 %UPDATE LIST OPEN WITH THE SUCCESSOR NODES
 %OPEN LIST FORMAT
 %--------------------------------------------------------------------------
 %IS ON LIST 1/0 |X val |Y val |Parent X val |Parent Y val |h(n) |g(n)|f(n)|
 %--------------------------------------------------------------------------
 %EXPANDED ARRAY FORMAT
 %--------------------------------
 %|X val |Y val ||h(n) |g(n)|f(n)|
 %--------------------------------
    for i=1:exp_count
        flag=0;
        for j=1:OPEN_COUNT
            if(exp_array(i,1) == OPEN(j,2) && exp_array(i,2) == OPEN(j,3) )
                OPEN(j,8)=min(OPEN(j,8),exp_array(i,5)); %#ok<*SAGROW>
                if OPEN(j,8)== exp_array(i,5)
                %UPDATE PARENTS,gn,hn
                    OPEN(j,4)=xNode;
                    OPEN(j,5)=yNode;
                    OPEN(j,6)=exp_array(i,3);
                    OPEN(j,7)=exp_array(i,4);
                end;%End of minimum fn check
                flag=1;
            end;%End of node check
%         if flag == 1
%             break;
        end;%End of j for
        if flag == 0
            OPEN_COUNT = OPEN_COUNT+1;
            OPEN(OPEN_COUNT,:)=insert_open(exp_array(i,1),exp_array(i,2),xNode,yNode,exp_array(i,3),exp_array(i,4),exp_array(i,5));
        end;%End of insert new element into the OPEN list
    end;%End of i for
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 %END OF WHILE LOOP
 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
 %Find out the node with the smallest fn 
    index_min_node = min_fn(OPEN,OPEN_COUNT,xTarget,yTarget);
    if (index_min_node ~= -1)    
   %Set xNode and yNode to the node with minimum fn
        xNode=OPEN(index_min_node,2);
        yNode=OPEN(index_min_node,3);
        path_cost=OPEN(index_min_node,6);%Update the cost of reaching the parent node
        %pause(.25)
        
  %Move the Node to list CLOSED
        CLOSED_COUNT=CLOSED_COUNT+1;
        CLOSED(CLOSED_COUNT,1)=xNode;
        CLOSED(CLOSED_COUNT,2)=yNode;
        
        OPEN(index_min_node,1)=0;
     else
      %No path exists to the Target!!
        NoPath=0;%Exits the loop!
      end;%End of index_min_node check
    end;%End of While Loop
%Once algorithm has run The optimal path is generated by starting of at the
%last node(if it is the target node) and then identifying its parent node
%until it reaches the start node.This is the optimal path

    i=size(CLOSED,1);
    Optimal_path=[];
    xval=CLOSED(i,1);
    yval=CLOSED(i,2);
    i=1;
    Optimal_path(i,1)=xval;
    Optimal_path(i,2)=yval;
    i=i+1;
    if ( (xval == xTarget) && (yval == yTarget))
         inode=0;
   %Traverse OPEN and determine the parent nodes
      parent_x=OPEN(node_index(OPEN,xval,yval),4);%node_index returns the index of the node
      parent_y=OPEN(node_index(OPEN,xval,yval),5);
      if exist('ventana', 'var')
        delete(ventana);
        clear('centana');
      end
      while( parent_x ~= xStart || parent_y ~= yStart)
           Optimal_path(i,1) = parent_x;
           Optimal_path(i,2) = parent_y;
           %Get the grandparents:-)
           inode=node_index(OPEN,parent_x,parent_y);
           set(p,'XData',CLOSED(CLOSED_COUNT,1)+.5,'YData',CLOSED(CLOSED_COUNT,2)+.5);
           drawnow;
           parent_x=OPEN(inode,4);%node_index returns the index of the node
           parent_y=OPEN(inode,5);
           i=i+1;
       end;
     j=size(Optimal_path,1);
 %Plot the Optimal Path!
     p=plot(Optimal_path(j,1)+.5,Optimal_path(j,2)+.5,'bo');
     j=j-1;
     for i=j:-1:1
      set(p,'XData',Optimal_path(i,1)+.5,'YData',Optimal_path(i,2)+.5);
      drawnow ;
     end;
    plot(Optimal_path(:,1)+.5,Optimal_path(:,2)+.5);
    else
    pause(1);
     h=msgbox('Sorry, No path exists to the Target!','warn');
    uiwait(h,5);
    end

end;

clear all;



% --- Executes on button press in clear_button.
function clear_button_Callback(hObject, eventdata, handles)
% hObject    handle to clear_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
cla reset;
global MAX_X;
global MAX_Y;
global MAP;
global MAX_VAL;
global xWaypoint;
global yWaypoint;
global HEIGHT;
global img;
global LAST_HEIGHT;
clear xWaypoint;
clear yWaypoint;

switch get(get(handles.buttons,'SelectedObject'),'Tag') % Get Tag of selected object.
    case 'rcar'
        HEIGHT = 5;
    case 'car'
        HEIGHT = 20;
    case 'human'
        HEIGHT = 50;
    case 'plane'
        HEIGHT = 255;
    otherwise
        LAST_HEIGHT = HEIGHT;
end


MAX_X=10;
MAX_Y=10;
%Objects in each coordinate
MAP=2*(ones(MAX_X,MAX_Y));
MAX_VAL = 10;
img = zeros(MAX_X,MAX_Y);
% Obtain Obstacle, Target and Robot Position
% Initialize the MAP with input values
% Obstacle=-1,Target = 0,Robot=1,Space=2
j=0;
x_val = 1;
y_val = 1;
axis([1 MAX_X+1 1 MAX_Y+1])
grid on;
hold on;
n=0;%Number of Obstacles


% --- Executes on button press in waypoint_button.
function waypoint_button_Callback(hObject, eventdata, handles)
% hObject    handle to waypoint_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% hObject    handle to setObstables_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
h=msgbox('Select waypoints using the Left Mouse button,to select the last waypoint use the Right button');
  xlabel('Select waypoints using the Left Mouse button,to select the last waypoint use the Right button','Color','blue');
uiwait(h,5);
if ishandle(h) == 1
    delete(h);
end
global xWaypoint;
global yWaypoint;
but=1;
k=1;
while but == 1
    [xval,yval,but] = ginput(1);
    xval=floor(xval);
    yval=floor(yval);
    xWaypoint(k)=xval%Put on the closed list as well
    yWaypoint(k)=yval
    %texto='Waypoint' + k;
    k=k+1;
    plot(xval+.5,yval+.5,'yo');
    text(xval+1,yval+.5, 'Waypoint');
 end%End of While loop


% --- Executes on button press in load_image.
function load_image_Callback(hObject, eventdata, handles)
% hObject    handle to load_image (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
path = get(handles.file_path, 'String');
%disp(path);
%imagen de prueba: img/DEMP22.gif
global img;
global MAX_X;
global MAX_Y;
global MAP;

img = imread(path);
%imshow(img);
%imagesc(img)

imagesc(flipdim(imrotate(img, -270),1));
[nX,nY,~]=size(img);
MAX_X = nX;
MAX_Y = nY;
MAP=2*(ones(MAX_X,MAX_Y));
axis([1 MAX_X+1 1 MAX_Y+1]);
grid on;
hold on;
nSeg=15;
%img

set(gca,'xtick',linspace(0,nY,15+1),'xticklabel',[],...
    'xgrid','on','xcolor','w',...
    'ytick',linspace(0,nX,15+1),'ytickLabel',[],...
    'ygrid','on','ycolor','w',...
    'gridLineStyle','-','linewidth',1)



function file_path_Callback(hObject, eventdata, handles)
% hObject    handle to file_path (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of file_path as text
%        str2double(get(hObject,'String')) returns contents of file_path as a double


% --- Executes during object creation, after setting all properties.
function file_path_CreateFcn(hObject, eventdata, handles)
% hObject    handle to file_path (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes when selected object is changed in buttons.
function buttons_SelectionChangeFcn(hObject, eventdata, handles)
% hObject    handle to the selected object in buttons 
% eventdata  structure with the following fields (see UIBUTTONGROUP)
%	EventName: string 'SelectionChanged' (read only)
%	OldValue: handle of the previously selected object or empty if none was selected
%	NewValue: handle of the currently selected object
% handles    structure with handles and user data (see GUIDATA)
global HEIGHT
global LAST_HEIGHT
switch get(eventdata.NewValue,'Tag') % Get Tag of selected object.
    case 'rcar'
        HEIGHT = 5;
    case 'car'
        HEIGHT = 20;
    case 'human'
        HEIGHT = 50;
    case 'plane'
        HEIGHT = 255;
    otherwise
        LAST_HEIGHT = HEIGHT;
end
