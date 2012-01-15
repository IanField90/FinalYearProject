class QuizzesController < ApplicationController
  # GET /quizzes
  def index
    @quizzes = Quiz.all
  end
  
  # GET /quizzes/1
  def show
    @quiz = Quiz.find(params[:id])
  end

  # GET /parts/1/quizzes/new
  def new
    @part = Part.find(params[:part_id])
    @quiz = Quiz.new(params[:quiz])
    if is_user_admin
      render "new"
    else
      redirect_to part_path(@part)
    end
  end
  
  # GET /quizzes/1/edit
  def edit
    @quiz = Quiz.find(params[:id])
    if !is_user_admin
      redirect_to part_path(@quiz.part_id)
    end
  end

  # POST /quizzes
  def create
    if is_user_admin
      @quiz = Quiz.new(params[:quiz])
      if @quiz.save
        redirect_to @quiz, :notice => "Quiz successfully created"
      else
        render :action => "new"
      end
    else
      redirect_to courses_path
    end
  end

  # PUT /quizzes/1
  def update
    @quiz = Quiz.find(params[:id])
    if is_user_admin
      #can update
      if @quiz.update_attributes(params[:quiz])
        redirect_to @quiz, :notice => 'Quiz was successfully updated.'
      else
        render :action => "edit"
      end
    else
      redirect_to part_path(@quiz.part_id)
    end
  end
  
  # DELETE /quizzes/1
  def destroy
    @quiz = Quiz.find(params[:id])
    if is_user_admin
      @quiz.destroy
    end
    redirect_to part_path(@quiz.part_id)
  end

end
