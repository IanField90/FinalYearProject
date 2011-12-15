class CoursesController < ApplicationController
  before_filter :is_user_admin, :except => [:index, :show]
  
  # GET /courses
  def index
    @courses = Course.all
    respond_to do |format|
      format.html
      format.json { render :json => @courses }
      format.xml { render :xml => @courses }
    end
  end

  # GET /courses/new
  def new
    # current_user is nill if no-one is logged in, check for that, then if they are admin
    @course = Course.new
    if is_user_admin
      render :new
    else
      redirect_to courses_path
    end
  end
  
  # GET /courses/1
  def show
    @course = Course.find(params[:id])
   respond_to do |format|
     format.html #show.html.erb
     format.json { render :json => @course }
     format.xml { render :xml => @course }
   end
  end
  
  # GET /courses/1/edit
  def edit
    @course = Course.find(params[:id])
    if !is_user_admin
      redirect_to courses_path
    end
  end
  
  # POST /courses
  def create
    if is_user_admin
      @course = Course.new(params[:course])
      
      if @course.save
        redirect_to @course, :notice => 'Course was successfully created.'
      else
        render :action => "new"
      end
    else
      redirect_to courses_path
    end
  end

  # PUT /courses/1
  def update
    @course = Course.find(params[:id])
    if @course.update_attributes(params[:course])
      redirect_to @course, :notice => 'Course was successfully updated.'
    else
      render :action => "edit"
    end
    #render :update
  end

  # DELETE /courses/1
  def destroy
    #render :destroy
    @course = Course.find(params[:id])
    @course.destroy
    redirect_to courses_path
  end

end
